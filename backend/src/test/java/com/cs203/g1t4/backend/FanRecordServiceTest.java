package com.cs203.g1t4.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.fanRecord.FanRecordResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.FanRecord;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidUsernameException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import com.cs203.g1t4.backend.repository.FanRecordRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.services.FanRecordService;

@ExtendWith(MockitoExtension.class)
public class FanRecordServiceTest {
    @Mock
    private FanRecordRepository fanRecordRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private FanRecordService fanRecordService;

    private User existingUser;
    private FanRecord fanRecord1;
    private FanRecord fanRecord2;

    private List<FanRecord> existingUserFanRecords;

    private List<String> listOfSpotifyArtists;

    @BeforeEach
    void setUp() {
        // arrange for all test cases
        existingUser = User.builder()
                .id("1234")
                .firstName("Alice")
                .lastName("Tan")
                .username("AliceTan")
                .email("aliceTan@test.com")
                .password("12345678")
                .phoneNo("91234567")
                .userCreationDate(LocalDateTime.now())
                .countryOfResidence("Singapore")
                .address("Sentosa Cove Avenue 1")
                .postalCode("S123456")
                .city("Singapore")
                .state("Singapore")
                .isPreferredMarketing(false)
                .spotifyAccount(null)
                .build();

        fanRecord1 = FanRecord.builder()
                .id("5678")
                .userId("1234")
                .artistId("3456")
                .registerDate(LocalDateTime.now())
                .build();

        fanRecord2 = FanRecord.builder()
                .id("5679")
                .userId("1234")
                .artistId("3457")
                .registerDate(LocalDateTime.now())
                .build();

        existingUserFanRecords = List.of(fanRecord1, fanRecord2);

        listOfSpotifyArtists = List.of(
                fanRecord1.getArtistId(),
                fanRecord2.getArtistId()
        );

    }

    @Test
    void findAllFanRecordsUnderUser_ValidUsername_ReturnFanRecordResponse() {
        // arrange
        String usernameRequest = "AliceTan";

        // mock userRepository "findByUsername"
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // mock fanRecordRepository "findFanRecordByUserId"
        when(fanRecordRepository.findFanRecordByUserId(any(String.class))).thenReturn(existingUserFanRecords);

        // act
        Response response = fanRecordService.findAllFanRecordsUnderUser(usernameRequest);

        // assert
        assertTrue(response instanceof FanRecordResponse);
        FanRecordResponse fanRecordResponse = (FanRecordResponse) response;
        assertEquals(existingUserFanRecords, fanRecordResponse.getAllFanRecords());

        verify(userRepository).findByUsername("AliceTan");
        verify(fanRecordRepository).findFanRecordByUserId(existingUser.getId());
    }

    @Test
    void findAllFanRecordsUnderUser_InvalidUsername_ThrowInvalidUsernameException() {
        // arrange
        String usernameRequest = "BobTan";

        // mock userRepository "findByUsername"
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidUsernameException e = assertThrows(InvalidUsernameException.class, () -> {
            fanRecordService.findAllFanRecordsUnderUser(usernameRequest);
        });

        // assert
        verify(userRepository).findByUsername("BobTan");
    }

    @Test
    void updateRecordsFromSpotify_ValidUsernameAndExistingFanRecord_Return() {
        // arrange
        String usernameRequest = "AliceTan";

        // mock userRepository "findByUsername"
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // mock artistRepository "findById"
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(new Artist()));

        // mock fanRecordRepository "findFanRecordByUserIdAndArtistId"
        when(fanRecordRepository.findFanRecordByUserIdAndArtistId("1234", "3456")).thenReturn(Optional.of(fanRecord1));
        when(fanRecordRepository.findFanRecordByUserIdAndArtistId("1234", "3457")).thenReturn(Optional.of(fanRecord2));

        // act
        fanRecordService.updateRecordsFromSpotify(listOfSpotifyArtists, usernameRequest);

        // assert
        verify(userRepository).findByUsername(usernameRequest);
        verify(fanRecordRepository, times(2)).findFanRecordByUserIdAndArtistId(any(String.class), any(String.class));
        verify(fanRecordRepository, times(0)).save(any(FanRecord.class));
    }

    @Test
    void updateRecordsFromSpotify_ValidUsernameAndNotExistingFanRecord_Return() {
        // arrange
        String usernameRequest = "AliceTan";

        List<String> newSpotifyArtistList = List.of(
                "3460", "3461"
        );

        // mock userRepository "findByUsername"
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // mock artistRepository "findById"
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(new Artist()));

        // mock fanRecordRepository "findFanRecordByUserIdAndArtistId"
        when(fanRecordRepository.findFanRecordByUserIdAndArtistId("1234", "3460")).thenReturn(Optional.empty());
        when(fanRecordRepository.findFanRecordByUserIdAndArtistId("1234", "3461")).thenReturn(Optional.empty());

        // act
        fanRecordService.updateRecordsFromSpotify(newSpotifyArtistList, usernameRequest);

        // assert
        verify(userRepository).findByUsername(usernameRequest);
        verify(fanRecordRepository, times(2)).findFanRecordByUserIdAndArtistId(any(String.class), any(String.class));
        verify(fanRecordRepository, times(2)).save(any(FanRecord.class));
    }

    @Test
    void updateRecordsFromSpotify_InvalidUsername_ThrowInvalidUsernameException() {
        // arrange
        String usernameRequest = "BobTan";

        List<String> newSpotifyArtistList = List.of(
                "3460", "3461"
        );

        // mock userRepository "findByUsername"
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidUsernameException e = assertThrows(InvalidUsernameException.class, () -> {
            fanRecordService.updateRecordsFromSpotify(newSpotifyArtistList, usernameRequest);
        });

        // assert
        verify(userRepository).findByUsername(usernameRequest);
    }

    @Test
    void updateRecordsFromSpotify_InvalidArtist_ThrowInvalidArtistIdException() {
        // arrange
        String usernameRequest = "AliceTan";

        List<String> newSpotifyArtistList = List.of(
                "3460", "3461"
        );

        // mock userRepository "findByUsername"
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // mock artistRepository "findById"
        when(artistRepository.findById(any(String.class))).thenThrow(new InvalidArtistIdException("3460"));

        // act
        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
            fanRecordService.updateRecordsFromSpotify(newSpotifyArtistList, usernameRequest);
        });

        // assert
        assertEquals("Artist with artistId: 3460 does not exists", e.getMessage());
        verify(userRepository).findByUsername(usernameRequest);
    }
}
