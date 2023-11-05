package com.cs203.g1t4.backend;

import static org.bson.assertions.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.service.serviceImpl.JwtServiceImpl;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Spy
    private JwtServiceImpl jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    private User existingUser;

    private String expiredJwtToken;

    @BeforeEach
    void setUp() {
        existingUser = User.builder()
                .id("1234")
                .firstName("John")
                .lastName("Doe")
                .username("John Doe")
                .email("JohnDoe@test.com")
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

        ReflectionTestUtils.setField(jwtService,
                "SECRET_KEY",
                // fake jwt secret key generated for the purpose of "getSignInKey" method
                "mMcbb8BCMuzXsqSyhOmVd8DDF4cW0XvDORkg5mktLA4kB2Ak0HoE++YqHRToPo1+GVOekHaNIYhjANPO248iIQ==");

        // generated JWT token for the purpose of testing
        // using https://jwt.io/ jwt token generator
        expiredJwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.4oIAeKm6Um6sdcbrv07vwRwEzfB3I6FS89s-bpG5d1s";
    }

    @Test
    void generateToken_FromUser_ReturnString() {

        // act
        String token = jwtService.generateToken(existingUser);

        // assert
        assertNotNull(token);

    }


    @Test
    void extractUsername_FromToken_ReturnString() {

        // act
        String token = jwtService.extractUsername(expiredJwtToken);

        // assert
        assertNotNull(token);

    }

//    @Test   // not working
//    void isTokenValid_FromToken_ReturnString() {
//
//        // act
//        boolean isValid = jwtService.isTokenValid(expiredJwtToken, any(UserDetails.class));
//
//        // mock String equals method
//        when(String.class.equals(any(String.class))).thenReturn(true);
//
//        // assert
//        assertTrue(isValid);
//    }

}
