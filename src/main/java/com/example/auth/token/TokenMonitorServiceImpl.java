
package com.example.auth.token;

import com.example.auth.common.enums.SessionStatus;
import com.example.auth.entity.UserSession;
import com.example.auth.session.SessionRepository;
import com.example.auth.token.dto.TokenStatusResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TokenMonitorServiceImpl
        implements TokenMonitorService {

    private final SessionRepository sessionRepository;
    private final Clock clock;

    public TokenMonitorServiceImpl(
            SessionRepository sessionRepository,
            Clock clock
    ) {
        this.sessionRepository = sessionRepository;
        this.clock = clock;
    }

    @Override
    @Transactional(readOnly = true)
    public TokenStatusResponse getTokenStatus(UUID sessionId) {
        UserSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Session not found")
                );

        return new TokenStatusResponse(
                session.getId(),
                resolveStatus(session),
                session.getExpiresAt(),
                session.getLastAccessedAt()
        );
    }

    private SessionStatus resolveStatus(UserSession session) {
        if (session.getRevokedAt() != null) {
            return SessionStatus.REVOKED;
        }

        OffsetDateTime now = OffsetDateTime.now(clock);

        if (!session.getExpiresAt().isAfter(now)) {
            return SessionStatus.EXPIRED;
        }

        return SessionStatus.ACTIVE;
    }
}
