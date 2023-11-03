package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.response.fanRecord.FanRecordResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FanRecordService {

    FanRecordResponse findAllFanRecordsUnderUser(String username);

    // helper method
    void updateRecordsFromSpotify(List<String> topListsOfArtist, String username);
}
