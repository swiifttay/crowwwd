package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.fanRecord.FanRecordRequest;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.fanRecord.FanRecordResponse;
import com.cs203.g1t4.backend.models.FanRecord;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicateFanRecordException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.repository.FanRecordRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FanRecordService {
    private final FanRecordRepository fanRecordRepository;
    private final UserRepository userRepository;

    public SuccessResponse createFanRecord(FanRecordRequest fanRecordRequest, String username) {
        // check if the user exists
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        String userId = user.getId();

        String artistId = fanRecordRequest.getArtistId();

        // check if there is such a record already
        Optional<FanRecord> duplicateFanRecord = fanRecordRepository.findFanRecordByUserIdAndArtistId(artistId, userId);

        if (duplicateFanRecord.isPresent()) {
            throw new DuplicateFanRecordException(artistId);
        }

        FanRecord fanRecord = FanRecord.builder()
                .artistId(artistId)
                .userId(userId)
                .registerDate(LocalDateTime.now())
                .build();

        return SuccessResponse.builder()
                .response("Fan Record successfully created")
                .build();
    }

    public FanRecordResponse findAllFanRecordsUnderUser(String username) {
        // get the user
        User user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidTokenException());

        // look for all the user id
        List<FanRecord> allFanRecordsUnderUser = fanRecordRepository.findFanRecordByUserId(user.getId());

        return FanRecordResponse.builder()
                .allFanRecords(allFanRecordsUnderUser)
                .build();

    }
}
