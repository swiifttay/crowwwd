package com.cs203.g1t4.backend.service.services;

import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.exceptions.unauthorisedLoginException.InvalidTokenException;

@Service
public interface ProfileService {

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
    Response updateProfile(UpdateProfileRequest request, String username)
            throws InvalidTokenException;

    /**
     * Finds a user from the repository.
     * If original user cannot be found based on username from token, throw a InvalidTokenException.
     *
     * @param username a String object containing the username of the user
     * @return SingleUserResponse containing the User Object
     */
    Response findProfile(String username);

    /**
     * Validates the spotify user that is connected with the user that is logged in 
     * 
     * @param spotifyUserId a String object containing the userId of the spotify user that logged in
     * @param username a String object containing the logged in user's username
     * @return boolean value true if this is the first time the loggedin user connects to spotify
     *                                  or if the spotify account user matches the loggedin user 
     *                          false if the spotify account user does not match the loggedin user
     */
    boolean validate(String spotifyUserId, String username);
}
