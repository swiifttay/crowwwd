package com.cs203.g1t4.backend.data.request.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event Name is required")
    private String name;

    @NotBlank(message = "Event Image Name is required")
    private String eventImageName; // imageName to look for in S3 Bucket

    @NotBlank(message = "Event Description is required")
    @Size(max = 30, message = "Event Description must be at most {max} characters long")
    private String description;

    //Dates should be parsed it in String format of "<yyyy>-<MM>-<dd>T<HH>:<mm>:<ss>", example: 2011-12-03T10:15:30
    @NotBlank(message = "Event Dates is required")
    private String[] dates; // date and time of the actual concert

    @NotBlank(message = "Event Venue is required")
    private String venue;

    @NotBlank(message = "Event Categories is required")
    private String[] categories;

    @NotBlank(message = "Artist ID is required")
    private String artistId;

    @NotBlank(message = "Event Seating Image Plan is required")
    private String seatingImagePlan; // imageName to look for in S3 Bucket

    //Dates should be parsed it in String format of "<yyyy>-<MM>-<dd>T<HH>:<mm>:<ss>", example: 2011-12-03T10:15:30
    @NotBlank(message = "Event Ticket Sales Date is required")
    private String[] ticketSalesDate; // date and time at which the ticket sales will be available
}
