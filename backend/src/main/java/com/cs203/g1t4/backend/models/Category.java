package com.cs203.g1t4.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Category {
    private String category;

    private int price;

    private String seatsInformationString;

    private int availableSeats;
}
