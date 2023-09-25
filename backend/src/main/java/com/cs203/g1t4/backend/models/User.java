package com.cs203.g1t4.backend.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document("user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements UserDetails { // Implements UserDetails so that the security.core library can be used
    // GETTER and SETTER removed as they are provided for by the Data annotation
    @Id
    private String id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    private String phoneNo;

    private LocalDateTime userCreationDate;

    private String countryOfResidence;

//    private String dateOfBirth;

    private String address;

    private String postalCode;

    private String city;

    private String state;

    private boolean isPreferredMarketing;

    private String spotifyAccount;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Methods to extend UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(() -> "USER");
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
