package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterRequest request) {

        //If error found, exception will be thrown
        Response response = authenticationService.register(request);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@Valid @RequestBody AuthenticationRequest request) {

        //If error found, exception will be thrown
        Response response = authenticationService.authenticate(request);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Response> findUsername(@PathVariable String username) {

        //If error found, exception will be thrown
        Response response = authenticationService.findUsername(username);

        //Else, return ok response
        return ResponseEntity.ok(response);

    }
}
