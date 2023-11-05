package com.cs203.g1t4.backend;

import com.amazonaws.services.workdocs.model.UpdateUserRequest;
import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.CommonService;
import com.cs203.g1t4.backend.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
    private ProfileService profileService;

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
    void updateProfile_ValidUsernameAndValidFullUpdateRequest_ReturnSuccessResponse() {
        // arrange
        String username = "AliceTan";

        UpdateProfileRequest updateUserRequest = UpdateProfileRequest.builder()
                .firstName("Alice")
                .lastName("Tan")
                .username("aliceTan")
                .email("aliceTan@test.com")
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

        // mock userRepository "findByUsername" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));



        // act


        // assert
    }



}
