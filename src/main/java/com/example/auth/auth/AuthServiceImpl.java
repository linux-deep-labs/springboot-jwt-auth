
package com.example.auth.auth;

import com.example.auth.auth.dto.LoginRequest;
import com.example.auth.auth.dto.LoginResponse;
import com.example.auth.auth.dto.RefreshTokenRequest;
import com.example.auth.entity.User;
import com.example.auth.entity.UserSession;
import com.example.auth.session.SessionService;
import com.example.auth.token.RefreshTokenService;
import com.example.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        // 1. ตรวจสอบว่ามี username นี้ในระบบ
        User user = userRepository.findByUsername(
                request.getUsername()
        ).orElseThrow(() ->
                new org.springframework.security.core.userdetails
                        .UsernameNotFoundException(
                        "Invalid username or password"
                )
        );

        // 2. ตรวจสอบสถานะบัญชี
        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new org.springframework.security.authentication
                    .DisabledException("Account is not active");
        }

        // 3. ตรวจสอบ username และ password ด้วย Spring Security
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        // 4. สร้าง Access Token
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String accessToken =
                jwtService.generateAccessToken(userDetails);
        UserSession session = sessionService.createSession(user);

        String refreshToken =
                refreshTokenService.createRefreshToken(user, session);

        // 5. ส่ง LoginResponse กลับ
        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirationSeconds())
                .expiresAt(
                        OffsetDateTime.ofInstant(
                                jwtService.getAccessTokenExpiration(),
                                ZoneOffset.UTC
                        )
                )
                .refreshToken(refreshToken)
                .user(LoginResponse.UserInfo.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(
                                user.getRoles()
                                        .stream()
                                        .map(role -> role.getName())
                                        .collect(java.util.stream.Collectors.toSet())
                        )
                        .build())
                .build();
    }
}
