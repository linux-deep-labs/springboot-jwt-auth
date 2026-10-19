
package com.example.auth.token.dto;

import com.example.auth.common.enums.SessionStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TokenStatusResponse(
        UUID sessionId,
        SessionStatus status,
        OffsetDateTime expiresAt,
        OffsetDateTime lastAccessedAt
) {
}
