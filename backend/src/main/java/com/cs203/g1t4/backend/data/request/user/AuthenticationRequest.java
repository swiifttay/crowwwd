package com.cs203.g1t4.backend.data.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    //Size checks may not be required but in place to not perform checks if size criteria not fulfilled.
    @NotBlank(message = "Username is required")
    @Size(min = 6,
            max = 30,
            message = "Username must be between {min} and {max} characters long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8,
            max = 120,
            message = "Password must be between {min} and {max} characters long")
    private String password;
}
