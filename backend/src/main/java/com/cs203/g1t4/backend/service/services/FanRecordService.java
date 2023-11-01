package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.response.fanRecord.FanRecordResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FanRecordService {

    public FanRecordResponse findAllFanRecordsUnderUser(String username);

    // helper method
    public void updateRecordsFromSpotify(List<String> topListsOfArtist, String username);
}
