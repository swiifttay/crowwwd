package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.request.user.UserRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.PasswordDoNotMatchException;
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
public class CommonService {
    //    This defaultResponse is on purpose so that it @Autowired DefaultResponse class instead of Response implementation
    private final Response defaultResponse;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public String returnOldUsername(String token) {
        String jwt = token.substring(7);
        return jwtService.extractUsername(jwt);
    }

    /**
     * Creates User object from UserRequest object
     * If username can be found in the repository, throw a DuplicatedUsernameException.
     * For updating, if all three password fields are not the same or wrong oldPassword is inputted, throw a
     * PasswordDoNotMatchException
     *
     * @param userRequest a UserRequest object containing the new user info to be created/updated
     * @param oldUser a User object containing the user info of the user that has to be updated. null for creation
     * @return the User object that has been created/updated
     */
    public User getUserClassFromRequest(UserRequest userRequest, User oldUser)
            throws DuplicatedUsernameException, PasswordDoNotMatchException {

        //Initialise variable to contain username
        String username;

        /*
         * Consider 3 cases:
         * Case 1: [CreateProfile] Existing username provided
         *         [UpdateProfile] User wishes to change his username, fills up newUsername, which already exists.
         *         -> Throw new DuplicatedUsernameException()
         * Case 2: User does not wish to change his username and leaves his username as null.
         *         -> Change username in user object to oldUsername
         * Case 3: [CreateProfile] Unique username provided
         *         [UpdateProfile] User wishes to change his username and fills up his username, which do not exist
         *         -> Change username in user object to newUsername
         */
        //Case 1 handled by if-block
        if ((oldUser == null || userRequest.getUsername() != null) &&
            (userRepository.findByUsername(userRequest.getUsername()).isPresent())) {

            //If newUsername is already taken, throw new DuplicatedUsernameException()
            throw new DuplicatedUsernameException(userRequest.getUsername());

        //Case 2 handled by else-if block
        } else if (oldUser != null && userRequest.getUsername() == null) {

            //Set username in String username to oldUsername
            username = oldUser.getUsername();

        //Case 3 handled by else-block
        } else {

            //Set username in String username to oldUsername
            username = userRequest.getUsername();
        }

        //Creates User Object with UserRequest (Except password)
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(username)
                .email(userRequest.getEmail())
                .phoneNo(userRequest.getPhoneNo())
                .userCreationDate(LocalDateTime.now())
                .countryOfResidence(userRequest.getCountryOfResidence())
//                .dateOfBirth(userRequest.getDateOfBirth())
                .address(userRequest.getAddress())
                .postalCode(userRequest.getPostalCode())
                .city(userRequest.getCity())
                .state(userRequest.getState())
                .isPreferredMarketing(userRequest.isPreferredMarketing())
                .spotifyAccount(userRequest.getSpotifyAccount())
                .build();

        //Checks if UserRequest object is a RegisterRequest Object
        //If so, typecast UserRequest object to a RegisterRequest Object
        if (userRequest instanceof RegisterRequest registerRequest && oldUser == null) {
            //Set the password of the user to the encoded form from the request
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            //Returns the User Object
            return user;
        } else {
            user.setId(oldUser.getId());
        }

        //If the code reaches here, UserRequest object is a UpdateProfileRequest object
        //Typecast UserRequest object to a UpdateProfileRequest Object
        UpdateProfileRequest updateProfileRequest = (UpdateProfileRequest) userRequest;

        /*
         * Consider 4 cases:
         * Case 1: User does not wish to change his password and leaves any of the 3 password fields as null.
         *         -> Changes password in user object to oldPassword
         * Case 2: User wishes to change his password, fills up ALL 3 password fields but newPassword and
         *         RepeatNewPassword do not match.
         *         -> Throw new PasswordDoNotMatchException()
         * Case 3: User wishes to change his password, fills up ALL 3 password fields, newPassword and
         *         RepeatNewPassword matches but oldPassword is wrong.
         *         -> Throw new InvalidCredentialsException()
         * Case 4: User wishes to change his password, fills up ALL 3 password fields, newPassword and
         *         RepeatNewPassword matches and oldPassword is correct.
         *         -> Change password in user object to encoded newPassword
         */
        //Case 1 handled by if-block
        if ((updateProfileRequest.getOldPassword() == null ||
            updateProfileRequest.getNewPassword() == null ||
            updateProfileRequest.getRepeatNewPassword() == null)) {

            //Change password in user object to oldPassword
            user.setPassword(oldUser.getPassword());

        //Case 2, 3, 4 handled else-block
        } else {

            //Case 2 handled in if-block
            if (!(updateProfileRequest.getNewPassword().equals(updateProfileRequest.getRepeatNewPassword()))) {
                //Throw new InvalidCredentialsException()
                throw new PasswordDoNotMatchException();
            }


            //Case 3 handled in try-catch block
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                oldUser.getUsername(),
                                updateProfileRequest.getOldPassword()
                        )
                );

            } catch (BadCredentialsException e) {

                //Throws new PasswordDoNotMatchException()
                throw new PasswordDoNotMatchException();

            }

            //Case 4 if code reaches this point
            //Change password in user object to encoded newPassword
            user.setPassword(passwordEncoder.encode(updateProfileRequest.getNewPassword()));
        }

        return user;
    }
}
