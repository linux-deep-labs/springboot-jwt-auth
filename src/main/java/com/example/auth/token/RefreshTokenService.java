package com.example.auth.token;

import com.example.auth.entity.User;
import com.example.auth.entity.UserSession;

public interface RefreshTokenService {

    String createRefreshToken(User user, UserSession session);
}