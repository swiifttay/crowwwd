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


    /**
     * To allow a user to enter into the holding area. If the holding area has yet to be made,
     * this function will create it and then insert the user into the holding area
     * @param username a String object containing the user's username
     * @param eventId a String object containing the eventId of the event the user wants to buy tickets for
     * @return a Response containing the QueueingStatusValue of the user
     */
    public Response enterHoldingArea(String username, String eventId) {

        // get and verify the user and event
        User user = verifyUsername(username);
        Event event = verifyEventId(eventId);

        // get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // verify if this is the time to purchase
        boolean validityToPurchase = timeBetween(currentTime,
                event.getTicketSalesDate().get(0).minusMinutes(10),
                event.getDates().get(0));

        // if it is not valid time yet, throw an exception
        if (!validityToPurchase) {
            throw new InvalidPurchasingTimeException(event.getName());
        }

        // determine if the user is already in queue
        Optional<QueueStatus> userQueueStatus = queueStatusRepository
                .findQueueByUserIdAndEventId(user.getId(), eventId);

        // if user is in queue already return the status they are in currently
        if (userQueueStatus.isPresent()) {

            // return the QueueResponse
            return QueueResponse.builder()
                    .queueingStatus(userQueueStatus.get().getQueueStatus().getStatusName())
                    .build();
        }

        // if reach this code means user is not in queue yet
        LocalDateTime ticketSalesOpen = event.getTicketSalesDate().get(0);

        // determine if the user entered the queue early
        boolean isEarly = timeBetween(currentTime,
                ticketSalesOpen.minusMinutes(10),
                ticketSalesOpen);


        // determine if the user is a fan
        boolean isFan = verifyFan(user.getId(), event.getArtistId());

        // get the holding area for the event
        HoldingArea holdingAreaForEvent = getHoldingAreaForEvent(eventId);

        // get the two queues and implement a automatic comparator
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

        // save the updated queues into the repository
        holdingAreaRepository.save(holdingAreaForEvent);

        // create the queueStatus for user
        QueueStatus createdUserQueueStatus = QueueStatus.builder()
                .userId(user.getId())
                .queueStatus(QueueingStatusValues.HOLDING)
                .eventId(eventId)
                .isFan(isFan)
                .build();

        // save the queueStatus into the repository
        queueStatusRepository.save(createdUserQueueStatus);

        // return the current status information
        return QueueResponse.builder()
                .queueingStatus(createdUserQueueStatus.getQueueStatus().getStatusName())
                .build();
    }

    /**
     * To check the information within the holding area and push individuals to queues and purchasing,
     * while also providing information for the user's current queue status (after updating)
     * @param username a String object that contains the username of the user who wants to check
     * @param eventId a String object that contains the eventId of the queue the user wants to enter for
     * @return a Response containing the QueueingStatusValue
     */
    public Response getQueueStatus(String username, String eventId) {

        // verify the username and eventId provided
        User user = verifyUsername(username);
        Event event = verifyEventId(eventId);

        // determine if the user is in queue
        Optional<QueueStatus> userQueueStatus = findQueueStatusOfUser(username, eventId);

        // if user is not yet in queue at all throw missing status
        if (userQueueStatus.isEmpty()) {
            throw new MissingQueueException();
        }

        // determine which holding area this eventId takes
        HoldingArea holdingAreaForEvent = getHoldingAreaForEvent(eventId);

        // determine the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // get the time to purchase tickers
        LocalDateTime ticketSalesTime = event.getTicketSalesDate().get(0);

        // if it is early then update to say not yet time for purchasing tickets, please hold
        if (timeBetween(currentTime,
                ticketSalesTime.minusMinutes(10),
                ticketSalesTime)) {

            // return holding value
            return QueueResponse.builder()
                    .queueingStatus(QueueingStatusValues.HOLDING.getStatusName())
                    .build();
        }

        // if reach here means that sales are open, can provide users with id

        // determine what should the new queueNumber be
        int queuesMade = holdingAreaForEvent.getQueuesMade();

        // determine if there has been any queues made
        boolean isQueuesMade = false;

        // this should max out at 30 to determine that 30 people are in a set of queue
        int newQueueSizeCounter = 0;

        // early queue counter
        int earlyQueueCounter = 0;

        // get the earlyQueue
        List<String> holdingAreaEarlyQueue = holdingAreaForEvent.getEarlyQueue();

        // determine and check whether there is anyone still in the early queue
        // if there is then start sending them into the respective queue numbers
        while (holdingAreaEarlyQueue != null && earlyQueueCounter < holdingAreaEarlyQueue.size() - 1) {

            // get the first person in queue and update them with queueNumber
            enterQueue(holdingAreaEarlyQueue.get(earlyQueueCounter), queuesMade, eventId);

            // update the number of people in the queue
            newQueueSizeCounter++;

            // update the earlyQueueCounter to see the next person in the queue
            earlyQueueCounter++;

            // change queueSet
            if (newQueueSizeCounter >= 30) {
                queuesMade++;
                newQueueSizeCounter = 0;

                // update the queuesMade information
                holdingAreaForEvent.setQueuesMade(queuesMade);
            }

            // update that queues were made
            isQueuesMade = true;
        }

        // once there is no one in the early queue, proceed to shifting the normal queue

        // get the normal queue
        List<String> holdingAreaNormalQueue = holdingAreaForEvent.getNormalQueue();

        // normal queue counter
        int normalQueueCounter = 0;

        // determine if there is enough people to keep creating a new queue
        while (holdingAreaNormalQueue != null && normalQueueCounter < holdingAreaNormalQueue.size() - 30) {

            // get the first person in queue
            enterQueue(holdingAreaNormalQueue.get(normalQueueCounter), queuesMade, eventId);

            // update the number of people in the queue
            newQueueSizeCounter++;

            // update the normalQueueCounter value
            normalQueueCounter++;

            // change queueSet
            if (newQueueSizeCounter >= 30) {
                queuesMade++;
                newQueueSizeCounter = 0;

                // update the queuesMade information
                holdingAreaForEvent.setQueuesMade(queuesMade);
            }

            // update that queues were made
            isQueuesMade = true;
        }


        // a fall through in case theres not enough people to make a new queue
        // but it has been awhile since the last queue was made
        // so look at the last time a queue was made
        if (currentTime
//                .isAfter(holdingAreaForEvent.getLastQueueCreateTime().plusMinutes(5))) {
                .isAfter(holdingAreaForEvent.getLastQueueCreateTime().plusSeconds(15))) {

            // loop until there's no more
            while (holdingAreaNormalQueue != null && holdingAreaNormalQueue.size() > normalQueueCounter) {

                // get the first person in queue
                enterQueue(holdingAreaNormalQueue.get(normalQueueCounter), queuesMade, eventId);

                // update the number of people in the queue
                newQueueSizeCounter++;

                // update the normalQueueCounter information
                normalQueueCounter++;

                // change queueSet
                if (newQueueSizeCounter >= 30) {
                    queuesMade++;
                    newQueueSizeCounter = 0;

                    // update the queuesMade information
                    holdingAreaForEvent.setQueuesMade(queuesMade);
                }

                // update that queues were made
                isQueuesMade = true;
            }
        }

        // update information on when the last queues were made
        holdingAreaForEvent.setLastQueueCreateTime(currentTime);

        if (isQueuesMade) {
            // update information on the next new queueId
            holdingAreaForEvent.setQueuesMade(queuesMade + 1);
        }

        // now determine if the queues to be purchasing should move forward
        // determine when was the last time a queue was pushed to purchase
        LocalDateTime holdingAreaLastQueueMoveToPurchaseTime = holdingAreaForEvent.getLastQueueMoveToPurchaseTime();

        // what was the latest queueNumber pushed to purchase
        int queuesToPurchase = holdingAreaForEvent.getQueuesToPurchase();

        // update the queueNumber pushed to purchase if it was 7 minutes after the last time a
        // queue was pushed to purchase and if there are queues to move forward
//        if (holdingAreaLastQueueMoveToPurchaseTime.isBefore(currentTime.minusMinutes(7)) && queuesToPurchase < queuesMade) {
            if (holdingAreaLastQueueMoveToPurchaseTime.isBefore(currentTime.minusSeconds(30)) && queuesToPurchase < queuesMade) {
            queuesToPurchase++;

            // update holdingArea information
            holdingAreaForEvent.setQueuesToPurchase(queuesToPurchase);
            holdingAreaForEvent.setLastQueueMoveToPurchaseTime(currentTime);
        }


        // get the user's queueStatus again
        QueueStatus userNewQueueStatus = queueStatusRepository.findQueueByUserIdAndEventId(user.getId(), eventId).orElseThrow(() -> new MissingQueueException());

        // determine if the user should be moved to ok
        QueueingStatusValues currentStatus = moveUserToPurchase(userNewQueueStatus.getId(), queuesToPurchase);

        // check the status and remove from holding area if they are off to purchase
        if (currentStatus.equals(QueueingStatusValues.OK)) {
            holdingAreaEarlyQueue.remove(user.getId());
            holdingAreaNormalQueue.remove(user.getId());
            holdingAreaForEvent.setEarlyQueue(holdingAreaEarlyQueue);
            holdingAreaForEvent.setNormalQueue(holdingAreaNormalQueue);
        }

        // save into repository the updates to holding area
        holdingAreaRepository.save(holdingAreaForEvent);

        return QueueResponse.builder()
                .queueingStatus(currentStatus.getStatusName())
                .build();
    }

    // give the user a queue number
    public void enterQueue(String userId, int queueId, String eventId) {
        // query for the queueStatus
        QueueStatus queueStatus = queueStatusRepository.findQueueByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new MissingQueueException());

        // update the queueStatus
        queueStatus.setQueueStatus(QueueingStatusValues.PENDING);
        queueStatus.setQueueId(queueId);
        queueStatusRepository.save(queueStatus);
    }

    public Response getQueueSizes(String eventId, String username) {
        // verify the event
        Event event = verifyEventId(eventId);

        // verify the user
        User user = verifyUsername(username);
        String userId = user.getId();

        // find the holding area for the event
        HoldingArea holdingAreaForEvent = getHoldingAreaForEvent(eventId);

        // determine where the user is in the queue
        int userIndex = -1;

        // find for the user in the earlyQueue
        List<String> holdingAreaEarlyQueue = holdingAreaForEvent.getEarlyQueue();
        List<String> holdingAreaNormalQueue = holdingAreaForEvent.getNormalQueue();

        // determine where the user is in the earlyQueue
        userIndex = holdingAreaEarlyQueue.indexOf(userId);

        // if the user was not in early queue
        // check if they are in the normal queue
        if (userIndex < 0) {
            userIndex = holdingAreaNormalQueue.indexOf(userId);
        }

        // determine if the user is still not found
        if (userIndex < 0) {
            throw new MissingQueueException();
        }

        // now find out what is the total number of people waiting
        int totalQueueingAndWaiting = holdingAreaEarlyQueue.size() + holdingAreaNormalQueue.size();

        return QueueSizesResponse.builder()
                .userPosition(userIndex)
                .total(totalQueueingAndWaiting)
                .build();
    }

    // helping methods
    public QueueingStatusValues moveUserToPurchase(String queueStatusId, int queuesToPurchase) {
        // find for the queueStatus
        QueueStatus userQueueStatus = queueStatusRepository
                .findById(queueStatusId)
                .orElseThrow(() -> new MissingQueueException());

        // determine if this person should move to purchase
        if (userQueueStatus.getQueueStatus().equals(QueueingStatusValues.PENDING) &&
                    (userQueueStatus.getQueueId() <= queuesToPurchase)) {

            // if they are then update the queueStatus
            userQueueStatus.setQueueStatus(QueueingStatusValues.OK);
            queueStatusRepository.save(userQueueStatus);
        }

        // return the status
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
