package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.request.user.UserRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.fanRecord.FanRecordResponse;
import com.cs203.g1t4.backend.data.response.user.SingleUserResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.models.exceptions.InvalidUsernameException;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.serviceImpl.ProfileServiceImpl;
import com.cs203.g1t4.backend.service.services.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CommonService commonService;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private User existingUser;
    private User secondExistingUser;
    private LocalDateTime userCreationDateDefault;
    private LocalDateTime newUserCreationDate;

    @BeforeEach
    void setUp() {
        // arrange for all tests
        userCreationDateDefault = LocalDateTime.now();
        newUserCreationDate = LocalDateTime.now();

        existingUser = User.builder()
                .id("1234")
                .firstName("Alice")
                .lastName("Tan")
                .username("AliceTan")
                .email("aliceTan@test.com")
                .password("12345678")
                .phoneNo("91234567")
                .userCreationDate(userCreationDateDefault)
                .countryOfResidence("Singapore")
                .address("Sentosa Cove Avenue 1")
                .postalCode("S123456")
                .city("Singapore")
                .state("Singapore")
                .isPreferredMarketing(false)
                .spotifyAccount(null)
                .build();

        secondExistingUser = User.builder()
                .firstName("Bob")
                .lastName("Tan")
                .username("BobTan")
                .email("bobTan@test.com")
                .password("ABCDEFGH")
                .phoneNo("91234567")
                .userCreationDate(userCreationDateDefault)
                .countryOfResidence("Singapore")
                .address("Sentosa Cove Avenue 1")
                .postalCode("S123456")
                .city("Singapore")
                .state("Singapore")
                .isPreferredMarketing(false)
                .spotifyAccount(null)
                .build();

    }

    @Test
    void findProfile_ValidUsername_ReturnSingleUserResponse() {
        // arrange
        String username = "aliceTan";

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // act
        Response response = profileService.findProfile(username);

        // assert
        assertTrue(response instanceof SingleUserResponse);
        SingleUserResponse singleUserResponse = (SingleUserResponse) response;
        assertEquals(existingUser, singleUserResponse.getUser());
    }

    @Test
    void findProfile_InvalidUsername_ReturnSingleUserResponse() {
        // arrange
        String username = "CathyLim";

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidTokenException e = assertThrows(InvalidTokenException.class, () -> {
            profileService.findProfile(username);
        });

        // assert
        verify(userRepository).findByUsername("CathyLim");
    }

    @Test
    void searchProfile_ValidUsername_ReturnSingleUserResponse() {
        // arrange
        String username = "aliceTan";

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // act
        Response response = profileService.searchProfile(username);

        // assert
        assertTrue(response instanceof SingleUserResponse);
        SingleUserResponse singleUserResponse = (SingleUserResponse) response;
        assertEquals(existingUser, singleUserResponse.getUser());
    }

    @Test
    void searchProfile_InvalidUsername_ReturnInvalidUsernameException() {
        // arrange
        String username = "CathyLim";

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidUsernameException e = assertThrows(InvalidUsernameException.class, () -> {
            profileService.searchProfile(username);
        });

        // assert
        verify(userRepository).findByUsername("CathyLim");
    }

    @Test
    void validate_ValidSpotifyUserIdAndUsername_ReturnTrue() {
        // arrange
        String spotifyAccount = "aliceTanSpotify";
        existingUser.setSpotifyAccount(spotifyAccount);

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // act
        boolean result = profileService.validate(spotifyAccount, existingUser.getUsername());

        // assert
        assertTrue(result);

    }

    @Test
    void validate_InvalidSpotifyUserIdAndUsername_ReturnFalse() {
        // arrange
        String spotifyAccount = "aliceTanSpotify";
        existingUser.setSpotifyAccount("aliceSpotify");

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));


        // act
        boolean result = profileService.validate(spotifyAccount, existingUser.getUsername());

        // assert
        assertFalse(result);

    }


    @Test
    void validate_NewSpotifyUserIdAndUsername_ReturnTrue() {
        // arrange
        String spotifyAccount = "aliceTanSpotify";
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        User updatedUser = User.builder()
                .id("1234")
                .firstName("Alice")
                .lastName("Tan")
                .username("AliceTan")
                .email("aliceTan@test.com")
                .password("12345678")
                .phoneNo("91234567")
                .userCreationDate(userCreationDateDefault)
                .countryOfResidence("Singapore")
                .address("Sentosa Cove Avenue 1")
                .postalCode("S123456")
                .city("Singapore")
                .state("Singapore")
                .isPreferredMarketing(false)
                .spotifyAccount(spotifyAccount)
                .build();

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // mock userRepository "save" operation
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> {
            User savedUser = userCaptor.getValue();
            return savedUser;
        });

        // act
        boolean result = profileService.validate(spotifyAccount, existingUser.getUsername());

        // assert
        assertTrue(result);
        verify(userRepository).save(userCaptor.getValue());
    }
    @Test
    public void updateUserProfile_ValidRequestAndUsername_returnSuccessResponse() {

        // arrange
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .phoneNo("97654321")
                .userCreationDate(newUserCreationDate)
                .countryOfResidence("Singapore-update")
                .address("Sentosa Cove Avenue 1-update")
                .postalCode("S654321")
                .city("Singapore-update")
                .state("Singapore-update")
                .isPreferredMarketing(false)
                .spotifyAccount(null)
                .build();

        User updatedUser = User.builder()
                .id("1234")
                .firstName("Alice-update")
                .lastName("Tan-update")
                .username("AliceTan")
                .email("aliceTan-update@test.com")
                .password("12345678")
                .phoneNo("97654321")
                .userCreationDate(newUserCreationDate)
                .countryOfResidence("Singapore-update")
                .address("Sentosa Cove Avenue 1-update")
                .postalCode("S654321")
                .city("Singapore-update")
                .state("Singapore-update")
                .isPreferredMarketing(false)
                .spotifyAccount(null)
                .build();

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // mock commonService "getUserClassFromRequest" method
        when(commonService.getUserClassFromRequest(any(UserRequest.class), any(User.class))).thenReturn(updatedUser);

        // act
        Response response = profileService.updateProfile(updateProfileRequest, existingUser.getUsername());

        // assert
        assertTrue(response instanceof SuccessResponse);
        SuccessResponse successResponse = (SuccessResponse) response;
        assertEquals("User has been updated successfully", successResponse.getResponse());
    }

    @Test
    public void updateUserProfile_ValidRequestAndInvalidUsername_returnSuccessResponse() {

        // arrange
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .phoneNo("97654321")
                .userCreationDate(newUserCreationDate)
                .countryOfResidence("Singapore-update")
                .address("Sentosa Cove Avenue 1-update")
                .postalCode("S654321")
                .city("Singapore-update")
                .state("Singapore-update")
                .isPreferredMarketing(false)
                .spotifyAccount(null)
                .build();

        String username = "CathyLim";

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidTokenException e = assertThrows(InvalidTokenException.class, () -> {
            profileService.updateProfile(updateProfileRequest, username);
        });

        // assert
        verify(userRepository).findByUsername("CathyLim");
    }
}
