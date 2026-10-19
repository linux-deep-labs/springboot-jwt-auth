
package com.example.auth.session;

import com.example.auth.entity.UserSession;
import com.example.auth.session.dto.SessionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(
            SessionRepository sessionRepository
    ) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponse> getMySessions(Long userId) {
        return sessionRepository.findAllByUser_Id(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void revokeSession(Long userId, UUID sessionId) {
        UserSession session = sessionRepository
                .findByIdAndUser_Id(sessionId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Session not found"
                        ));

        if (session.getRevokedAt() == null) {
            session.setRevokedAt(OffsetDateTime.now());
            session.setUpdatedAt(OffsetDateTime.now());
            sessionRepository.save(session);
        }
    }

    @Override
    @Transactional
    public void revokeAllSessions(Long userId) {
        List<UserSession> sessions =
                sessionRepository.findAllByUser_Id(userId);

        OffsetDateTime now = OffsetDateTime.now();

        for (UserSession session : sessions) {
            if (session.getRevokedAt() == null) {
                session.setRevokedAt(now);
                session.setUpdatedAt(now);
            }
        }

        sessionRepository.saveAll(sessions);
    }

    private SessionResponse toResponse(UserSession session) {
        return new SessionResponse(
                session.getId(),
                resolveStatus(session),
                session.getDeviceInfo(),
                session.getIpAddress() == null
                        ? null
                        : session.getIpAddress().getHostAddress(),
                session.getUserAgent(),
                session.getLastAccessedAt(),
                session.getExpiresAt(),
                session.getRevokedAt(),
                session.getCreatedAt()
        );
    }

    private String resolveStatus(UserSession session) {
        if (session.getRevokedAt() != null) {
            return "REVOKED";
        }

        if (!session.getExpiresAt().isAfter(OffsetDateTime.now())) {
            return "EXPIRED";
        }

        return "ACTIVE";
    }
}
