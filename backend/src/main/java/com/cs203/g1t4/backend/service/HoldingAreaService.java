package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.queue.QueueResponse;
import com.cs203.g1t4.backend.models.FanRecord;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidPurchasingTimeException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.models.exceptions.MissingQueueException;
import com.cs203.g1t4.backend.models.queue.HoldingArea;
import com.cs203.g1t4.backend.models.queue.QueueStatus;
import com.cs203.g1t4.backend.models.queue.QueueingStatusValues;
import com.cs203.g1t4.backend.repository.*;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class HoldingAreaService {
    private final HoldingAreaRepository holdingAreaRepository;
    private final FanRecordRepository fanRecordRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final QueueStatusRepository queueStatusRepository;


    public Response enterHoldingArea(String username, String eventId) {
        // get the user and event
        User user = verifyUsername(username);
        Event event = verifyEventId(eventId);

        verifyPurchaseValidity(event);

        Optional<QueueStatus> userQueueStatus = queueStatusRepository.findQueueByUserIdAndEventId(user.getId(), eventId);

        // if user is in holding area already return the status it is in
        if (userQueueStatus.isPresent()) {
            return QueueResponse.builder()
                    .queueingStatus(userQueueStatus.get().getQueueStatus().getStatusName())
                    .build();
        }

        // if reach this code means user is not in queue yet

        // determine if user is a fan
        boolean isFanOfEventArtist = verifyFan(user.getId(), event.getArtistId());

        // get the holding area for the event
        HoldingArea holdingAreaForEvent = getHoldingAreaForEvent(eventId);

        // determine which arraylist to add user to
        List<String> fans = holdingAreaForEvent.getFans();
        List<String> regulars = holdingAreaForEvent.getRegulars();

        // add user to the respective arrayList
        if (isFanOfEventArtist) {
            fans.add(user.getId());
        } else {
            regulars.add(user.getId());
        }

        // update the fans and regulars list
        holdingAreaForEvent.setFans(fans);
        holdingAreaForEvent.setRegulars(regulars);

        // create the queueStatus for user
        QueueStatus createdUserQueueStatus = QueueStatus.builder()
                .userId(user.getId())
                .queueStatus(QueueingStatusValues.HOLDING)
                .eventId(eventId)
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
        // determine if the user is in queue
        Optional<QueueStatus> userQueueStatus = findQueueStatusOfUser(username, eventId);

        // if user is in holding area already return the status it is in
        if (userQueueStatus.isEmpty()) {
            return QueueResponse.builder()
                    .queueingStatus(QueueingStatusValues.MISSING.getStatusName())
                    .build();
        }

        // determine which holding area this is
        HoldingArea holdingArea = getHoldingAreaForEvent(eventId);

        // determine the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // determine the last time a queue was made
        LocalDateTime holdingAreaLastQueueCreateTime = holdingArea.getLastQueueCreateTime();

        // create a new queue if it was 5 minutes after the last time
        // a queue was created
        if (holdingAreaLastQueueCreateTime.isBefore(currentTime.plusMinutes(5))) {
            int numQueuesMade = holdingArea.getQueuesMade();

            // update the holding area information
            holdingArea = makeQueuesInHoldingArea(holdingArea, userQueueStatus.get().getUserId());
            holdingArea.setQueuesMade(numQueuesMade + 1);
            holdingArea.setLastQueueCreateTime(currentTime);
        }

        // determine when was the last time a queue was pushed to purchase
        LocalDateTime holdingAreaLastQueueMoveToPurchaseTime = holdingArea.getLastQueueMoveToPurchaseTime();

        // what was the latest queueNumber pushed to purchase
        int queuesToPurchase = holdingArea.getQueuesToPurchase();

        // update the queueNumber pushed to purchase if it was 7 minutes after the last time a
        // queue was pushed to purchase
        if (holdingAreaLastQueueMoveToPurchaseTime.isBefore(currentTime.plusMinutes(7))) {
            queuesToPurchase++;

            // update holdingArea information
            holdingArea.setQueuesToPurchase(queuesToPurchase);
            holdingArea.setLastQueueMoveToPurchaseTime(currentTime);
        }

        // save into repository the updates to holding area
        holdingAreaRepository.save(holdingArea);

        // determine if the user is newly queued
        QueueingStatusValues currentStatus = moveUserToPurchase(userQueueStatus.get().getId(), queuesToPurchase);

        return QueueResponse.builder()
                .queueingStatus(currentStatus.getStatusName())
                .build();
    }

    // helping methods
    public QueueingStatusValues moveUserToPurchase(String queueStatusId, int queuesToPurchase) {
        QueueStatus userQueueStatus = queueStatusRepository
                .findById(queueStatusId)
                .orElseThrow(() -> new MissingQueueException());

        if (userQueueStatus.getQueueId() >= queuesToPurchase) {
            userQueueStatus.setQueueStatus(QueueingStatusValues.OK);
            queueStatusRepository.save(userQueueStatus);
        }

        return userQueueStatus.getQueueStatus();
    }

    // update 30 people into the new queue
    public HoldingArea makeQueuesInHoldingArea(HoldingArea holdingArea, String userId) {
        int newQueueId = holdingArea.getQueuesMade() + 1;
        Random rand = new Random();
        String eventId = holdingArea.getEventId();

        List<String> fans = holdingArea.getFans();
        List<String> regulars = holdingArea.getRegulars();

        int queueSize = Math.min(30, fans.size() + regulars.size());

        // make 30 of them
        for (int i = 0; i < queueSize; i++) {
            int percentageChance = 100 - rand.nextInt(100) + 1;
            List<String> updatingList = regulars;
            if (percentageChance >= 70 && !fans.isEmpty()) {
                updatingList = fans;
            } else if (regulars.isEmpty()) {
                updatingList = fans;
            }

            changeStatus(updatingList, percentageChance, eventId, newQueueId);
        }

        holdingArea.setFans(fans);
        holdingArea.setRegulars(regulars);
//        holdingAreaRepository.save(holdingArea);

        return holdingArea;
    }

    public void changeStatus(List<String> listToUpdate, int percentageChance, String eventId, int newQueueId) {

        // determine number to take from
        int numInList = (int) (percentageChance / 100.0 * listToUpdate.size());

        // determine who is the user, and also remove from the list
        String userId = listToUpdate.remove(numInList);

        // find the queueStatus of user
        QueueStatus queueStatus = queueStatusRepository.findQueueByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new MissingQueueException());

        // update the status
        queueStatus.setQueueId(newQueueId);
        queueStatus.setQueueStatus(QueueingStatusValues.PENDING);

        // update the queueStatusRepository
        queueStatusRepository.save(queueStatus);
    }


    public Optional<QueueStatus> findQueueStatusOfUser(String username, String eventId) {

        // get the user from the username
        User user = verifyUsername(username);

        // get the event from the eventId
        Event event = verifyEventId(eventId);

        // determine if it is time for buying tickets yet
        verifyPurchaseValidity(event);

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
                    .fans(new ArrayList<>())
                    .regulars(new ArrayList<>())
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

    public void verifyPurchaseValidity(Event event) {

        // determine if the current time is valid to buy tickets yet
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime salesDate = event.getTicketSalesDate().get(0);
        LocalDateTime eventDate = event.getDates().get(0);

        // if it is not yet time to buy tickets, throw the exception
        if (!(today.isAfter(salesDate.minusMinutes(5)) && today.isBefore(eventDate))) {
            throw new InvalidPurchasingTimeException(event.getName());
        }

        // determine if the event's sales time is now yet
        return;
    }

    public boolean verifyFan(String userId, String artistId) {

        // determine if the user is a fan
        Optional<FanRecord> userEventArtistFanRecord = fanRecordRepository
                .findFanRecordByUserIdAndArtistId(userId, artistId);
        return userEventArtistFanRecord.isPresent();
    }


}
