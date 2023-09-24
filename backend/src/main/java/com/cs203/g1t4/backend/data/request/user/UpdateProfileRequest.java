package com.cs203.g1t4.backend.data.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest implements UserRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String username;
    @NotNull
    private String email;
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;
    @NotNull
    private String phoneNo;
    private LocalDateTime userCreationDate;
    @NotNull
    private String countryOfResidence;
//    @NotNull
//    private String dateOfBirth;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String address;
    @NotNull
    private String postalCode;
    private boolean isPreferredMarketing;
    private String spotifyAccount;
}
