package com.cs203.g1t4.backend.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("order")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {

    @Id
    private String id;

    @NotBlank
    private String paymentId;

    @NotBlank
    private String[] seats;

    @NotBlank
    private String payingUserId;

    @NotBlank
    private String eventId;

    @NotBlank
    private String eventDate;

    @NotBlank
    private int totalCost;

    @NotBlank
    private int pricePerSeat;
}
