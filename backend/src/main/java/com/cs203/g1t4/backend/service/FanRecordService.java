package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.fanRecord.FanRecordRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.fanRecord.FanRecordResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.FanRecord;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicateFanRecordException;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.models.exceptions.InvalidUsernameException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
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
    private final ArtistRepository artistRepository;

    public Response findAllFanRecordsUnderUser(String username) {
        // get the user
        User user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidUsernameException(username));

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
                .orElseThrow(() -> new InvalidUsernameException(username));

        String userId = user.getId();


        for (String artistId : topListsOfArtist) {
            artistRepository.findById(artistId).orElseThrow(() -> new InvalidArtistIdException(artistId));

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
