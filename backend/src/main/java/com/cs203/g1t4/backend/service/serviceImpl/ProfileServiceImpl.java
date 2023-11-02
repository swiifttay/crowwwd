package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.SingleUserResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.services.CommonService;
import com.cs203.g1t4.backend.service.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
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

    /**
     * Validates the spotify user that is connected with the user that is logged in 
     * 
     * @param spotifyUserId a String object containing the userId of the spotify user that logged in
     * @param username a String object containing the logged in user's username
     * @return boolean value true if this is the first time the loggedin user connects to spotify
     *                                  or if the spotify account user matches the loggedin user 
     *                          false if the spotify account user does not match the loggedin user
     */
    public boolean validate(String spotifyUserId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        if (user.getSpotifyAccount() == null) {
                User updatedUser = User.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(username)
                        .email(user.getEmail())
                        .phoneNo(user.getPhoneNo())
                        .userCreationDate(LocalDateTime.now())
                        .countryOfResidence(user.getCountryOfResidence())
        //                .dateOfBirth(userRequest.getDateOfBirth())
                        .address(user.getAddress())
                        .postalCode(user.getPostalCode())
                        .city(user.getCity())
                        .state(user.getState())
                        .isPreferredMarketing(user.isPreferredMarketing())
                        .spotifyAccount(spotifyUserId)
                        .build();
                
                userRepository.save(updatedUser);

                return true;
        }

        return user.getSpotifyAccount().equals(spotifyUserId);
    }
}
