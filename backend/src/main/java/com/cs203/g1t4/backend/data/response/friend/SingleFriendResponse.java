package com.cs203.g1t4.backend.data.response.friend;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Friend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleFriendResponse implements Response {
    private Friend friend;
}
