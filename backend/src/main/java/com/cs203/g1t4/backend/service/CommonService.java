package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.user.AuthenticationRequest;
import com.cs203.g1t4.backend.data.request.user.RegisterRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.user.AuthenticationResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.InvalidCredentialsException;
import com.cs203.g1t4.backend.models.exceptions.MissingFieldsException;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommonService {
    //    This defaultResponse is on purpose so that it @Autowires DefaultResponse class instead of Response implementation
    private final Response defaultResponse;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public String returnOldUsername(String token) {
        String jwt = token.substring(7);
        return jwtService.extractUsername(jwt);
    }

    public String getIdByUsername(String username) {
        User origUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));
        return origUser.getId();
    }
}
