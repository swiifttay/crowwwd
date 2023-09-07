package com.cs203.g1t4.backend.data.request.event;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEventRequest {

    private String id;
    private String name;
    private String eventImageName; // imageName to look for in S3 Bucket
    private String description;
    private ArrayList<LocalDateTime> dates; // date and time of the actual concert
    private String venue;
    private ArrayList<String> categories;
    private String artistId;
    private String seatingImagePlan; // imageName to look for in S3 Bucket
    private ArrayList<LocalDateTime> ticketSalesDate; // date and time at which the ticket sales will be available
}
