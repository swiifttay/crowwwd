package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.data.response.user.SingleUserResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.*;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.remote.JMXAuthenticator;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public Response updateProfile(UpdateProfileRequest request, String username) {
        /*
         * If any missing fields (Exception of userCreationDate, isPreferredMarketing and spotifyAccount) present
         * throw new MissingFieldsException
         *
         * Room for change considering that user may not want to make changes to all fields all the time
         *
         * SECTION CAN BE REMOVED DUE TO ADDITION OF @VALID ANNOTATION
         */
//        if (request.getFirstName() == null || request.getLastName() == null || request.getPhoneNo() == null ||
//            request.getNationality() == null || request.getCountryOfResidence() == null ||
//            request.getCountryCode() == null || request.getGender() == null || request.getDateOfBirth() == null ||
//            request.getAddress() == null || request.getPostalCode() == null) {
//            throw new MissingFieldsException();
//        }

        //Finds user from repository, or else throw Invalid token exception
        User oldUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Create new user based on fields of request with id, username, password and userCreationDate taken from oldUser
        User newUser = User.builder()
                .id(oldUser.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(oldUser.getUsername())
                .email(request.getEmail())
                .password(oldUser.getPassword())
                .phoneNo(request.getPhoneNo())
                .userCreationDate(oldUser.getUserCreationDate())
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

        //Consider 2 cases:
        //Case 1: User wishes to change his username and fills up his username
        //Case 2: User does not wish to change his username and leaves his username as null.
        if (request.getUsername() != null) {

            //If new username is duplicated, throw new DuplicatedUsernameException
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new DuplicatedUsernameException(request.getUsername());
            }

            //If new username is not duplicated, change newUser
            newUser.setUsername(request.getUsername());

        }

        //Consider 2 cases:
        //Case 1: User wishes to change his password and fills up his password
        //Case 2: User does not wish to change his password and leaves his password as null.
        if ((request.getOldPassword() != null && request.getNewPassword() != null && request.getRepeatNewPassword() != null)) {

            //Checks if old password is correct
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                oldUser.getUsername(),
                                request.getOldPassword()
                        )
                );

            } catch (BadCredentialsException e) {

                //Throws an PasswordDoNotMatchException()
                throw new PasswordDoNotMatchException();

            }

            //Checks if newPassword and repeatNewPassword matches
            if (request.getNewPassword().equals(request.getRepeatNewPassword())) {
                newUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

            } else {

                //Throws an PasswordDoNotMatchException()
                throw new PasswordDoNotMatchException();
            }
        }

        //Saves user to repository
        userRepository.save(newUser);

        //If Everything goes smoothly, response will be created using AuthenticationResponse with token
        return SuccessResponse.builder()
                .response("User has been created successfully")
                .build();
    }

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
