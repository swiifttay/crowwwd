package com.cs203.g1t4.backend.data.request.user;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phoneNo;
    private LocalDateTime userCreationDate;
    private String nationality;
    private String countryOfResidence;
    private String countryCode;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String postalCode;
    private boolean isPreferredMarketing;
    private String spotifyAccount;

}
