
package com.example.auth.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.access-token-expiration-ms:900000}")
    private long accessTokenExpirationMs;

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        // TODO: Implement JWT generation using a JWT library.
        throw new UnsupportedOperationException(
                "JWT generation is not implemented"
        );
    }

    @Override
    public Instant getAccessTokenExpiration() {
        return Instant.now().plusMillis(accessTokenExpirationMs);
    }

    @Override
    public Long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationMs / 1000;
    }
}
