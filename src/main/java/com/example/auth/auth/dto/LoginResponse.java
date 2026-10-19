
package com.example.auth.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private OffsetDateTime expiresAt;

    private String refreshToken;

    private UserInfo user;

    @Getter
    @Builder
    public static class UserInfo {

        private Long id;

        private String username;

        private String email;

        private Set<String> roles;
    }
}
