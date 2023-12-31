package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.InvalidCredentialsException;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.services.AuthenticationService;
import com.cs203.g1t4.backend.service.services.CommonService;
import com.cs203.g1t4.backend.service.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;      //From ApplicationConfig.java
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CommonService commonService;

    /**
     *
     * @param request a RegisterRequest object containing the new user info to be created
     * @return a SuccessResponse containing information "User has been created successfully"
     *      or throws the relevant exception from the getUserClassFromRequest method in commonService
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
     *
     * @param request a AuthenticationRequest object containing the username and password of user to be authenticated
     * @return an AuthenticationResponse with information on the jwt token to be returned to the user for
     *      authenticated api path access
     */
    public Response authenticate(AuthenticationRequest request) {

        //Rethink logic and fix it for better understandability
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
     *
     * @param username a String object containing the username to be checked
     * @return a SuccessResponse "Username is available" if the username has not yet been used
     *      and therefore can be utilised for creating a new user
     *      otherwise throw DuplicatedUsernameException to show it has been used
     */
    public Response findUsername(String username) {
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
