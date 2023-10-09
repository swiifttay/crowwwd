package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Artist;
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

    public FullEvent returnFullEvent(Artist artist) {

        return FullEvent.builder()
                .eventId(id)
                .name(name)
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

    public ExploreEvent returnExploreEvent(String artistName) {

        return ExploreEvent.builder()
                .eventId(id)
                .name(name)
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
                .eventImageName(eventImageName)
                .dates(convertLocalDateTimeListToStrList(dates))
                .ticketSalesDate(convertLocalDateTimeListToStrList(ticketSalesDate))
                .venue(venue)
                .description(description)
                .build();
    }

    public List<String> convertLocalDateTimeListToStrList(List<LocalDateTime> list) {
        List<String> strList = new ArrayList<>();
        for (LocalDateTime localDateTime: list) {
            strList.add(localDateTime.toString());
        }
        return strList;
    }



}
