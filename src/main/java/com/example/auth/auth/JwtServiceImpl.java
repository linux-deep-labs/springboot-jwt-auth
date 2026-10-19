
package com.example.auth.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        // TODO: สร้าง JWT โดยใช้ JWT library
        // ต้องกำหนด signing key, subject, expiration
        // และ claims ที่จำเป็น

        throw new UnsupportedOperationException(
                "JWT generation is not implemented"
        );
    }

    @Override
    public Instant getAccessTokenExpiration() {
        // TODO: คืนเวลาหมดอายุตาม JWT configuration

        throw new UnsupportedOperationException(
                "JWT expiration is not configured"
        );
    }
}
