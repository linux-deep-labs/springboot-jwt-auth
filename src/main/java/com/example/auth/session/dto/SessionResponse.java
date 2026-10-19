
package com.example.auth.session.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SessionResponse(
        UUID id,
        String status,
        String deviceInfo,
        String ipAddress,
        String userAgent,
        OffsetDateTime lastAccessedAt,
        OffsetDateTime expiresAt,
        OffsetDateTime revokedAt,
        OffsetDateTime createdAt
) {
}
