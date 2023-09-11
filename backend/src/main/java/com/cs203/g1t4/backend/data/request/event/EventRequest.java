package com.cs203.g1t4.backend.data.request.event;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @NotNull
    private String name;
    @NotNull
    private String eventImageName; // imageName to look for in S3 Bucket
    @NotNull
    private String description;
    @NotNull
    private ArrayList<LocalDateTime> dates; // date and time of the actual concert
    @NotNull
    private String venue;
    @NotNull
    private ArrayList<String> categories;
    @NotNull
    private String artistId;
    @NotNull
    private String seatingImagePlan; // imageName to look for in S3 Bucket
    @NotNull
    private ArrayList<LocalDateTime> ticketSalesDate; // date and time at which the ticket sales will be available
}
