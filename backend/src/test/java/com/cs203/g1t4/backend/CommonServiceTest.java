package com.cs203.g1t4.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.request.user.UserRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.PasswordDoNotMatchException;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.serviceImpl.CommonServiceImpl;
import com.cs203.g1t4.backend.service.services.CommonService;
import com.cs203.g1t4.backend.service.services.JwtService;

@ExtendWith(MockitoExtension.class)
public class CommonServiceTest {

    @Mock
    private Response defaultResponse;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    private LocalDateTime userCreationDateDefault;
    private LocalDateTime newUserCreationDate;

    private User existingUser;
    private User secondExistingUser;
    private CommonServiceImpl commonService;


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

        commonService = new CommonServiceImpl(
                defaultResponse,
                jwtService,
                passwordEncoder,
                userRepository,
                authenticationManager

        );
    }

    @Test
    void returnOldUsername_ValidJWT_ReturnUsernameString() {
        // arrange
        String token = "bearer ABCDEFGHIJK";

        // mock jwtService "extractUsername" method
        when(jwtService.extractUsername(any(String.class))).thenReturn("username");

        // act
        String extractedUsername = commonService.returnOldUsername(token);

        // assert
        assertEquals("username", extractedUsername);
    }

    @Test
    void getUserClassFromRequest_CreatingNewUserWithValidDetails_ReturnNewUser() {
        // arrange
        UserRequest userRequest = RegisterRequest.builder()
                .firstName("Catherine")
                .lastName("Lim")
                .username("CatherineLim")
                .email("catherineLim@test.com")
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

        User user = User.builder()
                .firstName("Catherine")
                .lastName("Lim")
                .username("CatherineLim")
                .email("catherineLim@test.com")
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

        // mock userRepository "findByName" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // mock passwordEncoder "encode" method
        when(passwordEncoder.encode(any(String.class))).thenReturn("ABCDEFGH");

        // act
        User responseUser = commonService.getUserClassFromRequest(userRequest, null);

        // assert
        assertEquals(user, responseUser);
        verify(userRepository).findByUsername("CatherineLim");
    }

    @Test
    void getUserClassFromRequest_CreatingNewUserWithDuplicateName_ReturnDuplicatedUsernameException() {
        // arrange
        UserRequest userRequest = RegisterRequest.builder()
                .firstName("Alice")
                .lastName("Tan")
                .username("AliceTan")
                .email("aliceTan@test.com")
                .password("12345678")
                .phoneNo("91234567")
                .userCreationDate(newUserCreationDate)
                .countryOfResidence("Singapore")
                .address("Sentosa Cove Avenue 1")
                .postalCode("S123456")
                .city("Singapore")
                .state("Singapore")
                .isPreferredMarketing(false)
                .spotifyAccount(null)
                .build();

        // mock userRepository "findByName" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // act
        DuplicatedUsernameException e = assertThrows(DuplicatedUsernameException.class, () -> {
            commonService.getUserClassFromRequest(userRequest, null);
        });

        // assert
        verify(userRepository).findByUsername("AliceTan");
    }

    @Test
    void getUserClassFromRequest_UpdateUserKeepUsernameAndPassword_ReturnUpdatedUser() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
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

        // act
        User userResponse = commonService.getUserClassFromRequest(userRequest, existingUser);

        // assert
        assertEquals(updatedUser, userResponse);
    }


    @Test
    void getUserClassFromRequest_UpdateUserChangeValidUsernameKeepPassword_ReturnUpdatedUser() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .username("AliceTanUpdate")
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
                .username("AliceTanUpdate")
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

        // mock userRepository "findByName" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // act
        User userResponse = commonService.getUserClassFromRequest(userRequest, existingUser);

        // assert
        assertEquals(updatedUser, userResponse);
    }


    @Test
    void getUserClassFromRequest_UpdateUserChangeInvalidUsernameKeepPassword_ReturnDuplicatedUsernameException() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .username("BobTan")
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

        // mock userRepository "findByName" method
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(secondExistingUser));

        // act
        DuplicatedUsernameException e = assertThrows(DuplicatedUsernameException.class, () -> {
            commonService.getUserClassFromRequest(userRequest, secondExistingUser);
        });

        // assert
        verify(userRepository).findByUsername("BobTan");
    }


    @Test
    void getUserClassFromRequest_UpdateUserChangeValidPasswordKeepUsername_ReturnUpdatedUser() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .oldPassword("12345678")
                .newPassword("87654321")
                .repeatNewPassword("87654321")
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
                .password("87654321")
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

        // mock passwordEncoder "encode" method
        when(passwordEncoder.encode(any(String.class))).thenReturn("87654321");

        // act
        User userResponse = commonService.getUserClassFromRequest(userRequest, existingUser);

        // assert
        assertEquals(updatedUser, userResponse);
    }

    @Test
    void getUserClassFromRequest_UpdateUserChangeMissingOldPasswordKeepUsername_ReturnPasswordDoNotMatchException() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .newPassword("87654321")
                .repeatNewPassword("87654321")
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

        // act
        PasswordDoNotMatchException e = assertThrows(PasswordDoNotMatchException.class, () -> {
            commonService.getUserClassFromRequest(userRequest, existingUser);
        });

    }
    @Test
    void getUserClassFromRequest_UpdateUserChangeMissingNewPasswordKeepUsername_ReturnPasswordDoNotMatchException() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .oldPassword("12345678")
                .repeatNewPassword("87654321")
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

        // act
        PasswordDoNotMatchException e = assertThrows(PasswordDoNotMatchException.class, () -> {
            commonService.getUserClassFromRequest(userRequest, existingUser);
        });

    }

    @Test
    void getUserClassFromRequest_UpdateUserChangeMissingRepeatNetPasswordKeepUsername_ReturnPasswordDoNotMatchException() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .oldPassword("12345678")
                .newPassword("87654321")
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

        // act
        PasswordDoNotMatchException e = assertThrows(PasswordDoNotMatchException.class, () -> {
            commonService.getUserClassFromRequest(userRequest, existingUser);
        });

    }

    @Test
    void getUserClassFromRequest_UpdateUserWrongOldPasswordKeepUsername_ReturnPasswordDoNotMatchException() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .oldPassword("87654321")
                .newPassword("87654321")
                .repeatNewPassword("87654321")
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

        // mock authenticationManager "authenticate" method
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);

        // act
        PasswordDoNotMatchException e = assertThrows(PasswordDoNotMatchException.class, () -> {
            commonService.getUserClassFromRequest(userRequest, existingUser);
        });

    }

    @Test
    void getUserClassFromRequest_UpdateUserDifferentNewPasswordsKeepUsername_ReturnPasswordDoNotMatchException() {
        // arrange
        UserRequest userRequest = UpdateProfileRequest.builder()
                .firstName("Alice-update")
                .lastName("Tan-update")
                .email("aliceTan-update@test.com")
                .oldPassword("12345678")
                .newPassword("87654321")
                .repeatNewPassword("54321876")
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

        // act
        PasswordDoNotMatchException e = assertThrows(PasswordDoNotMatchException.class, () -> {
            commonService.getUserClassFromRequest(userRequest, existingUser);
        });

    }




}
