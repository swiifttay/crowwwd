package com.cs203.g1t4.backend.data.request.user;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements UserRequest{

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String phoneNo;
    private LocalDateTime userCreationDate;
    @NotNull
    private String countryOfResidence;
//    @NotNull
//    private String dateOfBirth;
    @NotNull
    private String address;
    @NotNull
    private String postalCode;
    @NotNull
    private String city;
    @NotNull
    private String state;
    private boolean isPreferredMarketing;
    private String spotifyAccount;
}
