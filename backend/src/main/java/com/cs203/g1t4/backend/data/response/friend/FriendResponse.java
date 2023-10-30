package com.cs203.g1t4.backend.data.response.friend;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Friend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendResponse implements Response {
    private List<Friend> friends;
}
