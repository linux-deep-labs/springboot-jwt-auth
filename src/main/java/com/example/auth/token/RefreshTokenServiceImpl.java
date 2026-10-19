
package com.example.auth.token;

import com.example.auth.entity.RefreshToken;
import com.example.auth.entity.User;
import com.example.auth.entity.UserSession;
import com.example.auth.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${security.jwt.refresh-token-expiration-days:7}")
    private long expirationDays;

    @Override
    @Transactional
    public String createRefreshToken(User user, UserSession session) {

        if (user == null || session == null) {
            throw new IllegalArgumentException(
                    "User and session are required"
            );
        }

        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String rawToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        String tokenHash = sha256(rawToken);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setSession(session);
        refreshToken.setTokenHash(tokenHash);
        refreshToken.setExpiresAt(now.plusDays(expirationDays));
        refreshToken.setCreatedAt(now);
        refreshToken.setUpdatedAt(now);

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    value.getBytes(StandardCharsets.UTF_8)
            );
            return HexFormat.of().formatHex(hash);
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Unable to hash refresh token", ex
            );
        }
    }
}
