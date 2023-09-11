package com.cs203.g1t4.backend.data.request.event;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    //Dates should be parsed it in String format of "<yyyy>-<MM>-<dd>T<HH>:<mm>:<ss>", example: 2011-12-03T10:15:30

    @NotNull
    private String name;
    @NotNull
    private String eventImageName; // imageName to look for in S3 Bucket
    @NotNull
    private String description;
    @NotNull
    private String[] dates; // date and time of the actual concert
    @NotNull
    private String venue;
    @NotNull
    private String[] categories;
    @NotNull
    private String artistId;
    @NotNull
    private String seatingImagePlan; // imageName to look for in S3 Bucket
    @NotNull
    private String[] ticketSalesDate; // date and time at which the ticket sales will be available
}
