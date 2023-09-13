package com.cs203.g1t4.backend.config.auth;

import com.cs203.g1t4.backend.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;    //Autowired from ApplicationConfig

    /*
     * Setting up of Cross-Origin Resource Sharing
     *
     * Defines a CORS filter that allows requests from a specific origin (http://localhost:3000)
     * and specifies the HTTP methods and headers that are allowed.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        //Create a new CorsConfiguration object, which will hold the CORS configuration settings
        CorsConfiguration configuration = new CorsConfiguration();
        //Specifies the allowed origins (i.e., domains) that are permitted to make cross-origin requests to your server
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        //Specify the HTTP methods that are allowed for cross-origin requests, which accepts all methods here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
        //Specify the headers that are allowed for cross-origin requests, which accepts all here
        configuration.setAllowedHeaders(Arrays.asList("*"));
        //Allows the inclusion of credentials (e.g., cookies) in cross-origin requests
        configuration.setAllowCredentials(true);
        //Creates a source for CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //Applies CORS configuration to all paths on your server
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                //Sets up CORS to handle cross-origin requests
        return http.cors(withDefaults())
                //Disables CSRF (Cross-Site Request Forgery) protection. Deprecated and can be removed
                .csrf().disable()
                //Configure authorization rules for your application. Include all requestMatchers within "{}"
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/**", "/error").permitAll().anyRequest().authenticated();
                })
                //Treats each request independently, and there is no server-side session state stored between requests.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Authentication provider is responsible for authenticating users based on their credentials.
                .authenticationProvider(authenticationProvider)
                //Adds custom JWT authentication filter (jwtAuthFilter) before the default UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //Builds the SecurityFilterChain
                .build();
    }
}
