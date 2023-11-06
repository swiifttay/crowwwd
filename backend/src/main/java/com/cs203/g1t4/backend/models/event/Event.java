package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.Venue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String alias;

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
     * Returns a FullEvent Object based on the current Event Object
     *
     * @param artist a Artist object obtained from the ArtistId of the Event object.
     * @return the FullEvent object converted from the Event object.
     */
    public FullEvent returnFullEvent(Artist artist, Venue venue) {

        return FullEvent.builder()
                .eventId(id)
                .name(name)
                .alias(alias)
                .eventImageName(eventImageName)
                .description(description)
                .dates(convertLocalDateTimeListToStrList(dates))
                .venue(venue)
                .categories(categories)
                .artist(artist)
                .seatingImagePlan(seatingImagePlan)
                .ticketSalesDate(convertLocalDateTimeListToStrList(ticketSalesDate))
                .build();
    }

    /**
     * Returns an ExploreEvent Object based on the current Event Object
     *
     * @param artistName a String object containing the name of the artist.
     * @return the ExploreEvent object converted from the Event object.
     */
    public ExploreEvent returnExploreEvent(String artistName) {

        return ExploreEvent.builder()
                .eventId(id)
                .name(name)
                .alias(alias)
                .eventImageName(eventImageName)
                .dates(convertLocalDateTimeListToStrList(dates))
                .categories(categories)
                .artistName(artistName)
                .build();
    }

    public DetailsEvent returnDetailsEvent(Venue venue) {

        return DetailsEvent.builder()
                .eventId(id)
                .name(name)
                .alias(alias)
                .eventImageName(eventImageName)
                .dates(convertLocalDateTimeListToStrList(dates))
                .ticketSalesDate(convertLocalDateTimeListToStrList(ticketSalesDate))
                .categories(categories)
                .venue(venue)
                .description(description)
                .build();
    }

    public TicketEvent returnTicketEvent(Venue venue, Artist artist) {
        return TicketEvent.builder()
                .eventId(id)
                .eventName(name)
                .artist(artist)
                .venueName(venue.getLocationName())
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
