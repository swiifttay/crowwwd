package com.cs203.g1t4.backend.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OutputFriend {

    @NotBlank
    private User friend;

    @NotBlank
    private boolean isApproved;
}
