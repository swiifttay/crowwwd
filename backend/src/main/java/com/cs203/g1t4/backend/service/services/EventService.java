package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface EventService {

    /**
     * Adds a new event to the repository.
     *
     * @param request a EventRequest object containing the new event info to be created
     * @param image a MultipartFile object containing the image corresponding to the event
     * @return SuccessResponse "Event has been created successfully"
     */
    // Main Service Methods
    Response addFullEvent(EventRequest request, MultipartFile image);

    /**
     * Deletes an event from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be deleted.
     * @return a SingleFullEventResponse object containing the deleted Event Object in the form of a FullEvent.
     */
    Response deleteFullEventById(String eventId);

    /**
     * Updates an event from the repository based on the eventId, request and image.
     *
     * @param eventId a String object containing the eventId of the event to be updated.
     * @param request a EventRequest object containing the new event info to be updated
     * @param image a MultipartFile object containing the new image to be updated
     * @return a SingleFullEventResponse object containing the updated Event Object in the form of a FullEvent.
     */
    Response updateFullEventById(String eventId, EventRequest request, MultipartFile image);

    /**
     * Finds an ExploreEvent from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be found.
     * @return a SingleFullEventResponse object containing the found Event Object in the form of an ExploreEvent.
     */
    Response getFullEventById(String eventId);

    /**
     * Finds a DetailsEvent from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be found.
     * @return a SingleDetailsEventResponse object containing the found Event Object in the form of an DetailsEvent.
     */
    Response getDetailsEventById(String eventId);

    /**
     * Finds a list of ExploreEvent from the repository that happens after today.
     *
     * @return a ExploreEventsResponse object containing the List of ExploreEvent objects.
     */
    Response getAllExploreEvents();

    Response getFullEventByAlias(String alias);
}
