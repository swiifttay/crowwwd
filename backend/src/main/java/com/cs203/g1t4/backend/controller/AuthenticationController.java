package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.ErrorResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User request) {

        //If error found, exception will be thrown
        Response response = authenticationService.register(request);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest request) {
        Response response = authenticationService.authenticate(request);

        //If AuthenticationErrorResponse found, handle as respective errors
        if (response instanceof ErrorResponse errorResponse) {
            return "Invalid Credentials".equals(errorResponse.getError())
                    ? ResponseEntity.badRequest().body(errorResponse)
                    : ResponseEntity.internalServerError().body(errorResponse);
        }

        //Else, return token to user
        return ResponseEntity.ok(response);
    }
}
