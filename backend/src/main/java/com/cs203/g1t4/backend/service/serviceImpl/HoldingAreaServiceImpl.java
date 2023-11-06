package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.queue.QueueResponse;
import com.cs203.g1t4.backend.data.response.queue.QueueSizesResponse;
import com.cs203.g1t4.backend.models.FanRecord;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidPurchasingTimeException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.models.exceptions.MissingQueueException;
import com.cs203.g1t4.backend.models.queue.HoldingArea;
import com.cs203.g1t4.backend.models.queue.HoldingAreaComparator;
import com.cs203.g1t4.backend.models.queue.QueueStatus;
import com.cs203.g1t4.backend.models.queue.QueueingStatusValues;
import com.cs203.g1t4.backend.repository.*;
import com.cs203.g1t4.backend.service.services.HoldingAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HoldingAreaServiceImpl implements HoldingAreaService {
    private final HoldingAreaRepository holdingAreaRepository;
    private final FanRecordRepository fanRecordRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final QueueStatusRepository queueStatusRepository;


    public Response enterHoldingArea(String username, String eventId) {
        // get the user and event
        User user = verifyUsername(username);
        Event event = verifyEventId(eventId);

        // get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // verify if this is the time to purchase
        boolean validityToPurchase = timeBetween(currentTime,
                event.getTicketSalesDate().get(0).minusMinutes(10),
                event.getDates().get(0));

        if (!validityToPurchase) {
            throw new InvalidPurchasingTimeException(event.getName());
        }

        Optional<QueueStatus> userQueueStatus = queueStatusRepository.findQueueByUserIdAndEventId(user.getId(), eventId);

        // if user is in holding area already return the status it is in
        if (userQueueStatus.isPresent()) {
            return QueueResponse.builder()
                    .queueingStatus(userQueueStatus.get().getQueueStatus().getStatusName())
                    .build();
        }

        // if reach this code means user is not in queue yet

        boolean isEarly = false;

        // determine if now we should enter the early queue
        if (timeBetween(currentTime,
                event.getTicketSalesDate().get(0).minusMinutes(10),
                event.getTicketSalesDate().get(0))
        ) {
            isEarly = true;
        }

        // determine if the user is a fan
        boolean isFan = verifyFan(user.getId(), event.getArtistId());

        // get the holding area for the event
        HoldingArea holdingAreaForEvent = getHoldingAreaForEvent(eventId);

        // get a random number generator
        Random rand = new Random();


        // get the two queues and implement a number generator
        PriorityQueue<String> earlyQueue = new PriorityQueue<>(holdingAreaForEvent.getEarlyQueue().size() + 1, new HoldingAreaComparator(isFan));

        PriorityQueue<String> normalQueue = new PriorityQueue<>(holdingAreaForEvent.getNormalQueue().size() + 1, new HoldingAreaComparator(isFan));

        // add the user to the queue
        if (isEarly) {
            earlyQueue.add(user.getId());
        } else {
            normalQueue.add(user.getId());
        }

        // insert into the holding area
        holdingAreaForEvent.setEarlyQueue(earlyQueue.stream().toList());
        holdingAreaForEvent.setNormalQueue(normalQueue.stream().toList());

        // save into repository
        holdingAreaRepository.save(holdingAreaForEvent);

        // create the queueStatus for user
        QueueStatus createdUserQueueStatus = QueueStatus.builder()
                .userId(user.getId())
                .queueStatus(QueueingStatusValues.HOLDING)
                .eventId(eventId)
                .isFan(isFan)
                .build();

        // save into repo
        holdingAreaRepository.save(holdingAreaForEvent);
        queueStatusRepository.save(createdUserQueueStatus);

        // return a status information
        return QueueResponse.builder()
                .queueingStatus(createdUserQueueStatus.getQueueStatus().getStatusName())
                .build();
    }


    public Response getQueueStatus(String username, String eventId) {

        // verify the username and eventId provided
        User user = verifyUsername(username);
        Event event = verifyEventId(eventId);

        // determine if the user is in queue
        Optional<QueueStatus> userQueueStatus = findQueueStatusOfUser(username, eventId);

        // if user is in holding area already return the status it is in
        if (userQueueStatus.isEmpty()) {
            return QueueResponse.builder()
                    .queueingStatus(QueueingStatusValues.MISSING.getStatusName())
                    .build();
        }

        // determine which holding area this is
        HoldingArea holdingAreaForEvent = getHoldingAreaForEvent(eventId);

        // determine the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // if it is early then update to say still holding
        if (timeBetween(currentTime,
                event.getTicketSalesDate().get(0).minusMinutes(10),
                event.getTicketSalesDate().get(0).minusMinutes(5))) {
            return QueueResponse.builder()
                    .queueingStatus(QueueingStatusValues.HOLDING.getStatusName())
                    .build();
        }

        // if reach here means that it is time to get the ids
        // determine what is the current queue number or if there is nothing
        int queuesMade = holdingAreaForEvent.getQueuesMade();

        // this should max out at 30 to determine that 30 people are in a set of queue
        int newQueueSizeCounter = 0;

        List<String> holdingAreaEarlyQueue = holdingAreaForEvent.getEarlyQueue();
        // start from the early queue
        // and start sending them into the respective queue numbers
        while (holdingAreaEarlyQueue.size() != 0) {

            // get the first person in queue
            enterQueue(holdingAreaEarlyQueue.remove(0), queuesMade, holdingAreaForEvent.getEventId());

            // update the number of people in the queue
            newQueueSizeCounter++;

            // change queueSet
            if (newQueueSizeCounter >= 30) {
                queuesMade++;
                newQueueSizeCounter = 0;
            }
        }

        // once that is done, do it for the normal queue,
        // get the normal queue
        List<String> holdingAreaNormalQueue = holdingAreaForEvent.getNormalQueue();

        // determine if there is enough people to keep creating a new queue
        if (holdingAreaNormalQueue.size() >= 30) {
            // loop until there's not enough
            while (holdingAreaNormalQueue.size() > 30) {

                // get the first person in queue
                enterQueue(holdingAreaNormalQueue.remove(0), queuesMade, holdingAreaForEvent.getEventId());

                // update the number of people in the queue
                newQueueSizeCounter++;

                // change queueSet
                if (newQueueSizeCounter >= 30) {
                    queuesMade++;
                    newQueueSizeCounter = 0;
                }
            }


        // otherwise look at the last time a queue was made
        } else if (currentTime.isAfter(
                holdingAreaForEvent.getLastQueueCreateTime().plusMinutes(5))) {

            // loop until there's not enough
            while (holdingAreaNormalQueue.size() != 0) {

                // get the first person in queue
                enterQueue(holdingAreaNormalQueue.remove(0), queuesMade, holdingAreaForEvent.getEventId());

                // update the number of people in the queue
                newQueueSizeCounter++;

                // change queueSet
                if (newQueueSizeCounter >= 30) {
                    queuesMade++;
                    newQueueSizeCounter = 0;
                }
            }
        }

        // now determine if the queues to be purchasing should move forward
        // determine when was the last time a queue was pushed to purchase
        LocalDateTime holdingAreaLastQueueMoveToPurchaseTime = holdingAreaForEvent.getLastQueueMoveToPurchaseTime();

        // what was the latest queueNumber pushed to purchase
        int queuesToPurchase = holdingAreaForEvent.getQueuesToPurchase();

        // update the queueNumber pushed to purchase if it was 7 minutes after the last time a
        // queue was pushed to purchase and if there are queues to move forward
        if (holdingAreaLastQueueMoveToPurchaseTime.isBefore(currentTime.minusMinutes(7)) && queuesToPurchase < queuesMade) {
            queuesToPurchase++;

            // update holdingArea information
            holdingAreaForEvent.setQueuesToPurchase(queuesToPurchase);
            holdingAreaForEvent.setLastQueueMoveToPurchaseTime(currentTime);
        }

        // save into repository the updates to holding area
        holdingAreaRepository.save(holdingAreaForEvent);

        // determine if the user is newly queued
        QueueingStatusValues currentStatus = moveUserToPurchase(userQueueStatus.get().getId(), queuesToPurchase);

        return QueueResponse.builder()
                .queueingStatus(currentStatus.getStatusName())
                .build();
    }

    // give the user a queue number
    public void enterQueue(String userId, int queueId, String eventId) {
        QueueStatus queueStatus = queueStatusRepository.findQueueByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new MissingQueueException());
        queueStatus.setQueueStatus(QueueingStatusValues.PENDING);
        queueStatus.setQueueId(queueId);
        queueStatusRepository.save(queueStatus);
    }

    public Response getQueueSizes(String eventId) {
        // verify the event
        Event event = verifyEventId(eventId);

        // get all the queues under the event
        List<QueueStatus> allQueues = queueStatusRepository.findByEventId(eventId);

        // create variables to count the number of people
        int countHolding = 0;
        int countPending = 0;
        for(QueueStatus queueStatus : allQueues) {
            if(queueStatus.getQueueStatus().equals(QueueingStatusValues.HOLDING)) {
                countHolding++;
            } else if (queueStatus.getQueueStatus().equals(QueueingStatusValues.PENDING)) {
                countPending++;
            }
        }

        return QueueSizesResponse.builder()
                .countHolding(countHolding)
                .countPending(countPending)
                .build();
    }

    // helping methods
    public QueueingStatusValues moveUserToPurchase(String queueStatusId, int queuesToPurchase) {
        QueueStatus userQueueStatus = queueStatusRepository
                .findById(queueStatusId)
                .orElseThrow(() -> new MissingQueueException());

        if (userQueueStatus.getQueueStatus().equals(QueueingStatusValues.PENDING) &&
                    (userQueueStatus.getQueueId() <= queuesToPurchase)) {
            userQueueStatus.setQueueStatus(QueueingStatusValues.OK);
            queueStatusRepository.save(userQueueStatus);
        }

        return userQueueStatus.getQueueStatus();
    }

    public Optional<QueueStatus> findQueueStatusOfUser(String username, String eventId) {

        // get the user from the username
        User user = verifyUsername(username);

        // get the event from the eventId
        Event event = verifyEventId(eventId);


        // check if this user is in holding area already
        Optional<QueueStatus> userQueueStatus = queueStatusRepository
                .findQueueByUserIdAndEventId(user.getId(), eventId);

        return userQueueStatus;
    }

    // find the holding area for the event who's eventId is the parameter
    public HoldingArea getHoldingAreaForEvent(String eventId) {
        Optional<HoldingArea> holdingAreaForEvent = holdingAreaRepository.findHoldingAreaByEventId(eventId);
        if (holdingAreaForEvent.isEmpty()) {
            LocalDateTime currentTime = LocalDateTime.now();

            HoldingArea holdingArea = HoldingArea.builder()
                    .eventId(eventId)
                    .earlyQueue(new ArrayList<>())
                    .normalQueue(new ArrayList<>())
                    .queuesToPurchase(0)
                    .queuesMade(0)
                    .lastQueueCreateTime(currentTime)
                    .lastQueueMoveToPurchaseTime(currentTime)
                    .build();

            holdingAreaRepository.save(holdingArea);
            return holdingArea;
        }

        return holdingAreaForEvent.get();

    }

    public User verifyUsername(String username) {
        // determine if user exists
        User user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidTokenException());
        return user;
    }

    public Event verifyEventId(String eventId) {
        // determine if event id is valid
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));
        return event;
    }

    public boolean timeBetween(LocalDateTime current, LocalDateTime start, LocalDateTime end) {
        return current.isBefore(end) && current.isAfter(start);
    }

    public boolean verifyFan(String userId, String artistId) {

        // determine if the user is a fan
        Optional<FanRecord> userEventArtistFanRecord = fanRecordRepository
                .findFanRecordByUserIdAndArtistId(userId, artistId);
        return userEventArtistFanRecord.isPresent();
    }


}
