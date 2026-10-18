package com.example.auth.dto.role;

import java.time.OffsetDateTime;

public record RoleResponse(
        Long id,
        String name,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}