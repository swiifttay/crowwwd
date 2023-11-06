package com.cs203.g1t4.backend.data.request.user;

import jakarta.validation.constraints.*;
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

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    //To be received as null from frontend if user does not wish to change his password
    //Need to consider validation of username size if parsed in?
    private String username;

    @NotBlank(message = "Email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            message = "Email must be valid")
    private String email;

    //To be received as null from frontend if user does not wish to change his password
    //Need to consider validation of password size if parsed in?
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "(6|8|9)\\d{7}", message = "Phone number must be valid")
    private String phoneNo;

    //Not necessary to be received as JSON input, but here to fulfill the getter method in UserRequest
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

    //Can be returned by frontend as empty
    private boolean isPreferredMarketing;

    //If empty, assume user closed his/her Spotify account
    //If full, assume user either changed his/her Spotify account or the spotify account is the same
    private String spotifyAccount;
}
