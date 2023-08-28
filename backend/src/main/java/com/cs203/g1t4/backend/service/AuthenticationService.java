package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.ErrorResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Response register(RegisterRequest request) {

        //If parts of the request is empty/not filled, return AuthenticationErrorResponse for Internal Server Error
        if (request.getEmail() == null || request.getPassword() == null) {
            return ErrorResponse.builder()
                    .error("Internal Server Error")
                    .message("One or more User fields are empty")
                    .build();
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        //If repository does not find a user of the same email in the repository, handle as usual
        try {

            //If user cannot be found in the repository, save user
            repository.save(user);

        } catch (DuplicateKeyException e) {

            //Else, return a AuthenticationErrorResponse for Bad Request
            return ErrorResponse.builder()
                    .error("Bad Request: Duplicated user email")
                    .message("The email is already found in the database, please proceed to login instead!")
                    .build();

        } catch (Exception e) {

            return ErrorResponse.builder()
                    .error("Unknown Error")
                    .message("An unknown error has occurred! Do try again!")
                    .build();
        }

        //If Everything goes smoothly, response will be created using AuthenticationResponse with token
        return SuccessResponse.builder()
                .response("User has been created successfully")
                .build();
    }

    public Response authenticate(AuthenticationRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            return ErrorResponse.builder()
                    .error("Internal Server Error")
                    .message("One or more User fields are empty")
                    .build              ();
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
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
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
