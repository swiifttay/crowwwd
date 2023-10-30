package com.cs203.g1t4.backend.data.request.friend;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {

    @NotBlank(message = "Friend ID is required")
    private String friendId;

}
