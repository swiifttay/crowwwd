package com.cs203.g1t4.backend;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.request.user.UserRequest;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.InvalidCredentialsException;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.serviceImpl.AuthenticationServiceImpl;
import com.cs203.g1t4.backend.service.services.CommonService;
import com.cs203.g1t4.backend.service.services.JwtService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private CommonService commonService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    private AuthenticationServiceImpl authenticationServiceImpl;
    private User existingUser;

    @BeforeEach
    void setUp() {
        // arrange for all tests
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

        // mock AuthenticationServiceImpl
        authenticationServiceImpl = new AuthenticationServiceImpl(
                userRepository,
                passwordEncoder,
                jwtService,
                authenticationManager,
                commonService
        );
    }

    @Test
    void register_NewUser_ReturnSuccessResponse() {
        // arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("Bob")
                .lastName("Tan")
                .username("BobTan")
                .email("bobTan@test.com")
                .password("12345678")
                .phoneNo("91234567")
                .userCreationDate(LocalDateTime.now())
                .countryOfResidence("Singapore")
                .address("Sentosa Cove Avenue 1")
                .postalCode("S123456")
                .city("Singapore")
                .state("Singapore")
                .build();

        User newUser = User.builder()
                .id("1234")
                .firstName("Bob")
                .lastName("Tan")
                .username("BobTan")
                .email("bobTan@test.com")
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


        // mock commonService "getUserFromClass" operation
        when(commonService.getUserClassFromRequest(any(UserRequest.class), eq(null))).thenReturn(newUser);

        // mock "save" operation
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // act
        SuccessResponse response = authenticationServiceImpl.register(registerRequest);

        // assert
        assertEquals("User has been created successfully", response.getResponse());
        verify(commonService).getUserClassFromRequest(registerRequest, null);
        verify(userRepository).save(newUser);
    }


    @Test
    void register_DuplicateUsername_ThrowDuplicateUsernameResponse() {
        // arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
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
                .build();

        DuplicatedUsernameException duplicatedUsernameException = new DuplicatedUsernameException("AliceTan");

        // mock commonService "getUserFromClass" operation
        when(commonService.getUserClassFromRequest(any(UserRequest.class), eq(null))).thenThrow(duplicatedUsernameException);

        DuplicatedUsernameException e = assertThrows(DuplicatedUsernameException.class, () -> {
            authenticationServiceImpl.register(registerRequest);
        });

        // assert
        assertEquals("User AliceTan exists", e.getMessage());
        verify(commonService).getUserClassFromRequest(registerRequest, null);
    }

    @Test
    void authenticate_CorrectCredentials_ReturnAuthenticationResponse() {

        // arrange
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("AliceTan")
                .password("12345678")
                .build();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                );

        // Mock the authenticationManager to not throw BadCredentialsException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        // Mock the behavior of userRepository.findByUsername to return the user
        when(userRepository.findByUsername(authenticationRequest.getUsername())).thenReturn(Optional.of(existingUser));

        // Mock the behaviour of generateToken in jwtService
        when(jwtService.generateToken(any(User.class))).thenReturn("your-token");

        // Act
        AuthenticationResponse response = authenticationServiceImpl.authenticate(authenticationRequest);

        // Assert
        assertTrue(response.getToken().equals("your-token"));
        verify(authenticationManager).authenticate(usernamePasswordAuthenticationToken);
        verify(userRepository).findByUsername(existingUser.getUsername());
        verify(jwtService).generateToken(existingUser);
    }

    @Test
    void authenticate_IncorrectCredentials_ReturnAuthenticationResponse() {

        // arrange
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("AliceTan")
                .password("87654321") // wrong password
                .build();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);
        // Act
        InvalidCredentialsException e = assertThrows(InvalidCredentialsException.class, () -> {
            authenticationServiceImpl.authenticate(authenticationRequest);
        });

        // Assert
    }

    @Test
    void findUsername_NewUsername_ReturnSuccessResponse() {
        // arrange
        String username = "CatherineLim";

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // act
        SuccessResponse response = authenticationServiceImpl.findUsername(username);

        // assert
        assertEquals("Username is available", response.getResponse());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void findUsername_DuplicateUsername_ThrowDuplicateUsernameException() {
        // arrange
        String username = "AliceTan";

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // act
        DuplicatedUsernameException e = assertThrows(DuplicatedUsernameException.class, () -> {
            authenticationServiceImpl.findUsername(username);
        });

        // assert
        verify(userRepository).findByUsername(username);
    }

}
