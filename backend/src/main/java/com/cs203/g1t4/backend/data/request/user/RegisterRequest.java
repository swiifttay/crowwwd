package com.cs203.g1t4.backend.data.request.user;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements UserRequest{

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 6,
            max = 30,
            message = "Username must be between {min} and {max} characters long")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,
            max = 120,
            message = "Password must be between {min} and {max} characters long")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "(6|8|9)\\d{7}", message = "Phone number must be valid")
    private String phoneNo;

    private LocalDateTime userCreationDate;

    @NotBlank(message = "Country Of Residence is required")
    private String countryOfResidence;

//    @NotNull
//    private String dateOfBirth;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Postal Code is required")
    private String postalCode;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    private boolean isPreferredMarketing;

    private String spotifyAccount;
}
