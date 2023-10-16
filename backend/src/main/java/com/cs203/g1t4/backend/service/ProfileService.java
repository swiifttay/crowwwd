package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.SingleUserResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.*;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;      //From ApplicationConfig.java
    private final AuthenticationManager authenticationManager;
    private final CommonService commonService;

    /**
     * Updates new user to the repository.
     * If original user cannot be found based on token, throw a InvalidTokenException.
     * If username can be found in the repository, throw a DuplicatedUsernameException.
     * For updating, if all three password fields are not the same or wrong oldPassword is inputted, throw a
     * PasswordDoNotMatchException
     *
     * @param request a RegisterRequest object containing the new user info to be updated
     * @param username a String object containing the username of the user originally
     * @return SuccessResponse "User has been updated successfully"
     */
    public Response updateProfile(UpdateProfileRequest request, String username)
            throws InvalidTokenException{

        //Finds user from repository, or else throw Invalid token exception
        User oldUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Uses getUserClassFromRequest from commonService to generate user class from request
        User newUser = commonService.getUserClassFromRequest(request, oldUser);

        //Saves user to repository
        userRepository.save(newUser);

        //If Everything goes smoothly, response will be created using AuthenticationResponse with token
        return SuccessResponse.builder()
                .response("User has been updated successfully")
                .build();
    }

    /**
     * Finds a user from the repository.
     * If original user cannot be found based on username from token, throw a InvalidTokenException.
     *
     * @param username a String object containing the username of the user
     * @return SingleUserResponse containing the User Object
     */
    public Response findProfile(String username) {

        //Finds user from repository, or else throw Invalid token exception
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Returns a SingleUserResponse if successful
        return SingleUserResponse.builder()
                .user(user)
                .build();
    }
}
