package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.user.UserRequest;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.duplicatedException.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.unauthorisedLoginException.PasswordDoNotMatchException;

public interface CommonService {

    String returnOldUsername(String token);

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
     User getUserClassFromRequest(UserRequest userRequest, User oldUser)
            throws DuplicatedUsernameException, PasswordDoNotMatchException;
}
