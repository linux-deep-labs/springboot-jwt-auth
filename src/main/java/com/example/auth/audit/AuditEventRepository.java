
package com.example.auth.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditEventRepository
        extends JpaRepository<AuditEvent, UUID> {
}
