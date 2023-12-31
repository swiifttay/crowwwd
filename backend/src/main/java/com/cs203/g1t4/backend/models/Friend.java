package com.cs203.g1t4.backend.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("friend")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Friend {

    @Id
    private String id;

    @NotBlank
    private String userId;

    @NotBlank
    private String friendId;

    @NotBlank
    private boolean isApproved;
}
