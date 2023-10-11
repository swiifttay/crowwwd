package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.InvalidCredentialsException;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;      //From ApplicationConfig.java
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CommonService commonService;

    /**
     * Adds a new user to the repository.
     * If username can be found in the repository, throw a DuplicatedUsernameException.
     * For updating, if all three password fields are not the same or wrong oldPassword is inputted, throw a
     * PasswordDoNotMatchException
     *
     * @param request a RegisterRequest object containing the new user info to be created
     * @return SuccessResponse "User has been created successfully"
     */
    public Response register(RegisterRequest request) {

        User user = commonService.getUserClassFromRequest(request, null);

        userRepository.save(user);

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("User has been created successfully")
                .build();
    }

    /**
     * Authenticate a user.
     * If authentication fails, throws InvalidCredentialsException.
     *
     * @param request a AuthenticationRequest object containing the username and password of user to be authenticated
     * @return SuccessResponse "User has been created successfully"
     */
    public Response authenticate(AuthenticationRequest request) throws InvalidCredentialsException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {

            //Throws an InvalidCredentialsException, if username or password is incorrect.
            throw new InvalidCredentialsException();

        }

        //If authenticated, create jwt token and return an AuthenticationResponse containing jwt token
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Finds if user with inputted username exists in repository.
     * If such a user exists, throws DuplicatedUsernameException.
     *
     * @param username a String object containing the username to be checked
     * @return SuccessResponse "Username is available"
     */
    public Response findUsername(String username) throws DuplicatedUsernameException {
        //If username exists, throw new DuplicatedUsernameException(username)
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicatedUsernameException(username);
        }

        //If Everything goes smoothly, response will be created using SuccessResponse
        return SuccessResponse.builder()
                .response("Username is available")
                .build();
    }
}
