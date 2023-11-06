package com.cs203.g1t4.backend.data.request;

import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @Id
    private String id;

    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    @NotBlank(message = "Seats is required")
    private String[] seats;

    @NotBlank(message = "Paying User ID is required")
    private String payingUserId;

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Event Date is required")
    private String eventDate;

    private int totalCost;

    private int pricePerSeat;
}
