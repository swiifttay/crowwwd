package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Artist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("event")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Event {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String eventImageName; // imageName to look for in S3 Bucket

    @NotBlank
    private String description;

    @NotBlank
    private List<LocalDateTime> dates; // date and time of the actual concert

    @NotBlank
    private String venue;

    @NotBlank
    private List<String> categories;

    @NotBlank
    private String artistId;

    @NotBlank
    private String seatingImagePlan; // imageName to look for in S3 Bucket

    @NotBlank
    private List<LocalDateTime> ticketSalesDate; // date and time at which the ticket sales will be available

    /**
     * Returns an OutputEvent Object based on the current Event Object
     *
     * @param artist a Artist object obtained from the ArtistId of the Event object.
     * @return the OutputEvent object converted from the Event object.
     */
    public OutputEvent returnOutputEvent(Artist artist) {

        return OutputEvent.builder()
                .eventId(id)
                .name(name)
                .description(description)
                .eventImageName(eventImageName)
                .dates(convertLocalDateTimeListToStrList(dates))
                .venue(venue)
                .categories(categories)
                .artist(artist)
                .seatingImagePlan(seatingImagePlan)
                .ticketSalesDate(convertLocalDateTimeListToStrList(ticketSalesDate))
                .build();
    }

    /**
     * Converts a List of LocalDateTime objects to a List of Strings for storage
     *
     * @param list a list of LocalDateTime objects
     * @return a list of String objects converted from the list of LocalDateTime object.
     */
    public List<String> convertLocalDateTimeListToStrList(List<LocalDateTime> list) {
        List<String> strList = new ArrayList<>();
        for (LocalDateTime localDateTime: list) {
            strList.add(localDateTime.toString());
        }
        return strList;
    }



}
