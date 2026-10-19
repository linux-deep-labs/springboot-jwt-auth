
package com.example.auth.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

public interface JwtService {

    String generateAccessToken(UserDetails userDetails);

    Instant getAccessTokenExpiration();
}
