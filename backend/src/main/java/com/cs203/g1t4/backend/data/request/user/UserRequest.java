package com.cs203.g1t4.backend.data.request.user;

import java.time.LocalDateTime;

public interface UserRequest {
    //Used for the purpose of creating a CommonService method to generate a User Class, which does not handle Passwords

    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getUsername();
    void setUsername(String username);

    String getEmail();
    void setEmail(String email);

    String getPhoneNo();
    void setPhoneNo(String phoneNo);

    LocalDateTime getUserCreationDate();
    void setUserCreationDate(LocalDateTime userCreationDate);

    String getCountryOfResidence();
    void setCountryOfResidence(String countryOfResidence);

//    String getDateOfBirth();
//    void setDateOfBirth(String dateOfBirth);

    String getAddress();
    void setAddress(String address);

    String getPostalCode();
    void setPostalCode(String postalCode);

    String getCity();
    void setCity(String city);

    String getState();
    void setState(String state);

    boolean isPreferredMarketing();
    void setPreferredMarketing(boolean preferredMarketing);

    String getSpotifyAccount();
    void setSpotifyAccount(String spotifyAccount);
}
