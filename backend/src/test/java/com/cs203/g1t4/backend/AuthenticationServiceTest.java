package com.cs203.g1t4.backend;


import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.AuthenticationServiceImpl;
import com.cs203.g1t4.backend.service.CommonService;
import com.cs203.g1t4.backend.service.JwtService;
import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

//    @Value("${crowwwd.backend.app.jwtSecretKey}")
//    private String JWT_SECRET_KEY;

    private JwtService jwtService;

//    private Key signInKey;
//    private String jwtToken;

    private CommonService commonService;

    private AuthenticationServiceImpl authenticationServiceImpl;
    @Mock
    private Response defaultResponse;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private  AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp(){
        jwtService = new JwtService();
//        signInKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("secret-key"));
//        jwtToken = "jwt-token";

        commonService = new CommonService(
                defaultResponse,
                jwtService,
                passwordEncoder,
                userRepository,
                authenticationManager
        );
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

        User user = User.builder()
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

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // mock "save" operation
        when(userRepository.save(any(User.class))).thenReturn(user);

        // act
        SuccessResponse response = authenticationServiceImpl.register(registerRequest);

        // assert
        assertEquals("User has been created successfully", response.getResponse());
        verify(userRepository).findByUsername(registerRequest.getUsername());
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

        User existingUser = User.builder()
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

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        DuplicatedUsernameException e = assertThrows(DuplicatedUsernameException.class, () -> {
            authenticationServiceImpl.register(registerRequest);
        });

        // assert
        assertEquals("User AliceTan exists", e.getMessage());
        verify(userRepository).findByUsername(registerRequest.getUsername());
    }

//    @Test
//    void authenticate_CorrectCredentials_ReturnAuthenticationResponse() {
//        // arrange
//        User existingUser = User.builder()
//                .id("1234")
//                .firstName("Alice")
//                .lastName("Tan")
//                .username("AliceTan")
//                .email("aliceTan@test.com")
//                .password("12345678")
//                .phoneNo("91234567")
//                .userCreationDate(LocalDateTime.now())
//                .countryOfResidence("Singapore")
//                .address("Sentosa Cove Avenue 1")
//                .postalCode("S123456")
//                .city("Singapore")
//                .state("Singapore")
//                .isPreferredMarketing(false)
//                .spotifyAccount(null)
//                .build();
//
//        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
//                .username("AliceTan")
//                .password("12345678")
//                .build();
//
//        // Mock the authenticationManager to not throw BadCredentialsException
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
//
//        // Mock the behavior of userRepository.findByUsername to return the user
//        when(userRepository.findByUsername(authenticationRequest.getUsername())).thenReturn(Optional.of(existingUser));
//
//        // Mock the behavior of jwtService to return a token
//        when(jwtService.generateToken(existingUser)).thenReturn("your-token");
//
//        // Act
//        AuthenticationResponse response = authenticationServiceImpl.authenticate(authenticationRequest);
//
//        // Assert
//        assertNotNull(response);
//        assertNotNull(response.getToken());
//        assertTrue(response.getToken().equals("your-token"));
//
////        // mock userRepository "findByUsername" operation
////        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));
////
////        // act
////        AuthenticationResponse authenticationResponse = authenticationServiceImpl.authenticate(authenticationRequest);
////
////        // assert
////        assertEquals("your-token", authenticationResponse.getToken());
////        verify(userRepository).findByUsername(authenticationRequest.getUsername());
//
//    }

    @Test
    void findUsername_NewUsername_ReturnSuccessResponse() {
        // arrange
        String username = "AliceTan";

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

        User existingUser = User.builder()
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

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        // assert
        DuplicatedUsernameException e = assertThrows(DuplicatedUsernameException.class, () -> {
            authenticationServiceImpl.findUsername(username);
        });
        verify(userRepository).findByUsername(username);
    }

}
