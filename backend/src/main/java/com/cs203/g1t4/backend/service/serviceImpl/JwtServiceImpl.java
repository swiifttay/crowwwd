package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.service.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${crowwwd.backend.app.jwtSecretKey}")
    private String SECRET_KEY;

    /**
     * Extract username from the token
     *
     * @param token a String object containing token
     * @return a String object containing a username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract username from the token
     *
     * @param token a String object containing token
     * @return a String object containing a username
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a token from User user
     *
     * @param user a User object containing the user
     * @return a String object containing the JWT token
     */
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT token
     *
     * @param extraClaim a Map<String, Object> object containing claims
     * @param user a User object containing the user
     * @return a String object containing the JWT token
     */
    public String generateToken(Map<String, Object> extraClaim, User user) {
        return Jwts
                .builder()
                .setClaims(extraClaim)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks is token is valid
     *
     * @param token a String object containing token
     * @param userDetails a UserDetails object containing the User
     * @return a boolean is token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks of token is expired
     *
     * @param token a String object containing token
     * @return a boolean if token is expired
     */
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract expiration of token
     *
     * @param token a String object containing token
     * @return a Data object
     */
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract all claims from the token
     *
     * @param token a String object containing token
     * @return a Claims object
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Get Sign In key
     *
     * @return a Key object from keyBytes
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

