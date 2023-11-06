package com.cs203.g1t4.backend.filter;

import com.cs203.g1t4.backend.service.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    public static final String[] whiteListedRoutes = new String[] {
        // normal user paths
        "/api/auth/register",
        "/api/auth/authenticate",
        "/api/auth/findUsername/.*",
        "/api/event/getEvent/.*",
        "/api/event/getAllEvents",
        "/api/event/getEventsBetween/start/.*?/end/.*?",

        // admin user (additional features in the future)
        "/api/event/fullEvent/.*",
        "/api/event/exploreEvent/all",

        // others
        "/api/spotify/get-user-code",
        "/error",

        // swagger
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/.*",
        "/swagger-resources/.*",
        "swagger-resources/.*",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/.*",
        "webjars/.*",
        "/swagger-ui.html"
    };

    // Purpose: doFilterInternal() method is called everytime an API is called which
    // does the checks accordingly
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        /*
         * Declaring variables that are immutable:
         * 1. String authHeader: Stores the Header String in request that contains
         * "Authorization".
         * 2. String jwt: Will be used to store the jwt obtained from a Header String
         * containing "Authorization".
         * 3. String username: Will be used to store the username obtained from jwt.
         */
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        /*
         * Checks the following:
         * 1. Checks if the API path is in the array of whiteListed paths
         * 2. Checks if the authHeader is empty (No authentication)
         * 3. Checks if the authHeader does not start with Bearer (No Bearer token)
         *
         */
        if (isWhiteListed(request.getServletPath())) {
            /*
             * If condition is met, the filterChain continues processing the request and
             * response without any additional
             * authentication or authorization checks. It effectively allows the request to
             * pass
             * through without requiring JWT validation.
             */
            filterChain.doFilter(request, response);

            // Returns prevents further processing of authentication and authorization logic
            // for this request.
            return;
        }

        // Obtains the JWT token from the String, substring(7) removes the "Bearer "
        // before the actual jwt token
        jwt = authHeader.substring(7);

        // Obtains the username from the JWT Token which is stored as a subject in the
        // JWT generated
        username = jwtService.extractUsername(jwt);

        /*
         * Checks the following:
         * 1. If there are no username, which only means that there is no JWT token
         * (Necessary?)
         * 2. Checks whether there is no authentication information available in the
         * security context.
         * null means that user has not been logged in
         */
        if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {

            // Returns a UserDetails object that is obtained from the repository
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // If token is valid.
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Create an Authentication Token from username and password in userDetails
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Builds an authentication token
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        /*
         * If condition is met, the filterChain continues processing the request and
         * response without any additional
         * authentication or authorization checks. It effectively allows the request to
         * pass
         * through without requiring JWT validation.
         */
        filterChain.doFilter(request, response);
    }

    public static boolean isWhiteListed(String url) {
        for (String pattern : whiteListedRoutes) {
            // Escape special characters and replace '*' with '.*' for regex matching
            // String regexPattern = pattern
            // .replace("/", "\\/") // Escape '/'
            // .replace(".", "\\.") // Escape '.'
            // .replace("*", ".*"); // Replace '*' with '.*' for regex matching

            if (url.matches(pattern)) {
                return true;
            }
        }
        return false;
    }

}
