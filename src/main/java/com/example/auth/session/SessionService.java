package com.example.auth.session;

import com.example.auth.entity.User;
import com.example.auth.entity.UserSession;
import com.example.auth.session.dto.SessionResponse;

import java.util.List;
import java.util.UUID;

public interface SessionService {

    UserSession createSession(User user);

    List<SessionResponse> getMySessions(Long userId);

    void revokeSession(Long userId, UUID sessionId);

    void revokeAllSessions(Long userId);
}