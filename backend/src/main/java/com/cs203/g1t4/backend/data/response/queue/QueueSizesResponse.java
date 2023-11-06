package com.cs203.g1t4.backend.data.response.queue;

import com.cs203.g1t4.backend.data.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueueSizesResponse implements Response {
    private int countHolding;
    private int countPending;
}
