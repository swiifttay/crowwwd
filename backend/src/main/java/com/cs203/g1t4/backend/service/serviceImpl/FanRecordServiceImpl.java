package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.response.fanRecord.FanRecordResponse;
import com.cs203.g1t4.backend.models.FanRecord;
import com.cs203.g1t4.backend.models.User;
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
public class FanRecordServiceImpl implements FanRecordService {
    private final FanRecordRepository fanRecordRepository;
    private final UserRepository userRepository;

    // not allowed to create a fan record manually because they should be via the spotify api
//    public SuccessResponse createFanRecord(FanRecordRequest fanRecordRequest, String username) {
//        // check if the user exists
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new InvalidTokenException());
//
//        String userId = user.getId();
//
//        String artistId = fanRecordRequest.getArtistId();
//
//        // check if there is such a record already
//        Optional<FanRecord> duplicateFanRecord = fanRecordRepository.findFanRecordByUserIdAndArtistId(artistId, userId);
//
//        if (duplicateFanRecord.isPresent()) {
//            throw new DuplicateFanRecordException(artistId);
//        }
//
//        FanRecord fanRecord = FanRecord.builder()
//                .artistId(artistId)
//                .userId(userId)
//                .registerDate(LocalDateTime.now())
//                .build();
//
//        return SuccessResponse.builder()
//                .response("Fan Record successfully created")
//                .build();
//    }

    public FanRecordResponse findAllFanRecordsUnderUser(String username) {
        // get the user
        User user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidTokenException());

        // look for all the user id
        List<FanRecord> allFanRecordsUnderUser = fanRecordRepository.findFanRecordByUserId(user.getId());

        return FanRecordResponse.builder()
                .allFanRecords(allFanRecordsUnderUser)
                .build();

    }

    // helper method
    public void updateRecordsFromSpotify(List<String> topListsOfArtist, String username) {
        // check if the user exists
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        String userId = user.getId();


        for (String artistId : topListsOfArtist) {

            // check if there is such a record already
            Optional<FanRecord> duplicateFanRecord = fanRecordRepository.findFanRecordByUserIdAndArtistId(userId, artistId);

            // if no records have been added, edit it
            if (!duplicateFanRecord.isPresent()) {
                FanRecord fanRecord = FanRecord.builder()
                        .artistId(artistId)
                        .userId(userId)
                        .registerDate(LocalDateTime.now())
                        .build();

                fanRecordRepository.save(fanRecord);
            }
        }
    }
}
