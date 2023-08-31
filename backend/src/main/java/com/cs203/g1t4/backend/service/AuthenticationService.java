package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.ErrorResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.MissingFieldsException;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Response register(User request) {

        //If any missing fields (Exception of userCreationDate, isPreferredMarketing and spotifyAccount)
        if (request.getFirstName() == null || request.getLastName() == null || request.getUsername() == null ||
            request.getPassword() == null || request.getPhoneNo() == null || request.getNationality() == null ||
            request.getCountryOfResidence() == null || request.getCountryCode() == null ||
            request.getGender() == null || request.getDateOfBirth() == null || request.getAddress() == null ||
            request.getPostalCode() == null) {
            throw new MissingFieldsException();
        }


            //If duplicated username, throw new DuplicatedUsernameException
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicatedUsernameException(request.getUsername());
        }

        /*
         * Can be optimized: Perhaps consider a clone method for user which creates
         * the entire user object and just set the variables to be changed
         */
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNo(request.getPhoneNo())
                .userCreationDate(LocalDateTime.now())
                .nationality(request.getNationality())
                .countryOfResidence(request.getCountryOfResidence())
                .countryCode(request.getCountryCode())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .postalCode(request.getPostalCode())
                .isPreferredMarketing(request.isPreferredMarketing())
                .spotifyAccount(request.getSpotifyAccount())
                .build();

        userRepository.save(user);

        //If Everything goes smoothly, response will be created using AuthenticationResponse with token
        return SuccessResponse.builder()
                .response("User has been created successfully")
                .build();
    }

    public Response authenticate(AuthenticationRequest request) {

        if (request.getUsername() == null || request.getPassword() == null) {
            return ErrorResponse.builder()
                    .error("Internal Server Error")
                    .message("One or more User fields are empty")
                    .build();
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {

            //Handles Exception if username/Password is incorrect, returns an AuthenticationErrorResponse
            return ErrorResponse.builder()
                    .error("Invalid Credentials")
                    .message("Email/Password is incorrect")
                    .build();

        } catch (Exception e) {

            return ErrorResponse.builder()
                    .error("Unknown Error")
                    .message("An unknown error has occurred! Do try again!")
                    .build();

        }

        //If authenticated, create jwt token and return an AuthenticationResponse
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
