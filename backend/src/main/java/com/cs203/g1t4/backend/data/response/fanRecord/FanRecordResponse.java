package com.cs203.g1t4.backend.data.response.fanRecord;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.FanRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FanRecordResponse implements Response {
    private List<FanRecord> allFanRecords;
}
