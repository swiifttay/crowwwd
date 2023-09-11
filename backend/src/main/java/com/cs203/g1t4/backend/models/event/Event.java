package com.cs203.g1t4.backend.models.event;


import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
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

    public OutputEvent returnOutputEvent() {

        //Create List<String> from List<LocalDateTime> dates
        List<String> dateStrList = new ArrayList<>();
        for (LocalDateTime localDateTime : dates) {
            dateStrList.add(localDateTime.toString());
        }

        //Create List<String> from List<LocalDateTime> ticketSalesDate
        List<String> ticketSalesDateStrList = new ArrayList<>();
        for (LocalDateTime localDateTime : ticketSalesDate) {
            ticketSalesDateStrList.add(localDateTime.toString());
        }

        OutputEvent out = OutputEvent.builder()
                .id(id)
                .name(name)
                .eventImageName(eventImageName)
                .description(description)
                .dates(dateStrList)
                .venue(venue)
                .categories(categories)
                .artistId(artistId)
                .seatingImagePlan(seatingImagePlan)
                .ticketSalesDate(ticketSalesDateStrList)
                .build();

        return out;
    }

}
