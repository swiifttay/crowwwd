package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.response.Response;

public interface AuthenticationService {

    Response register(RegisterRequest request);

    Response authenticate(AuthenticationRequest request);

    Response findUsername(String username);
}
