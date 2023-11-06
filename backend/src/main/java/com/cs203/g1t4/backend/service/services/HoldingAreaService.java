package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.queue.HoldingArea;
import com.cs203.g1t4.backend.models.queue.QueueStatus;
import com.cs203.g1t4.backend.models.queue.QueueingStatusValues;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface HoldingAreaService {

    public Response enterHoldingArea(String username, String eventId);

    public Response getQueueStatus(String username, String eventId);

    public Response getQueueSizes(String eventId);

    public QueueingStatusValues moveUserToPurchase(String queueStatusId, int queuesToPurchase);

    public HoldingArea makeQueuesInHoldingArea(HoldingArea holdingArea, String userId);

    public void changeStatus(List<String> listToUpdate, int percentageChange, String eventId, int newQueueId);

    public Optional<QueueStatus> findQueueStatusOfUser(String username, String eventId);

    public HoldingArea getHoldingAreaForEvent(String eventId);

    public User verifyUsername(String username);

    public Event verifyEventId(String eventId);

    public void verifyPurchaseValidity(Event event);

    public boolean verifyFan(String userId, String artistId);


}
