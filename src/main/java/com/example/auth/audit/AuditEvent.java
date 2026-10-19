
package com.example.auth.audit;

import com.example.auth.common.enums.LoginEventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "audit_events",
        indexes = {
                @Index(
                        name = "idx_audit_user_id",
                        columnList = "user_id"
                ),
                @Index(
                        name = "idx_audit_event_type",
                        columnList = "event_type"
                ),
                @Index(
                        name = "idx_audit_created_at",
                        columnList = "created_at"
                )
        }
)
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 40)
    private LoginEventType eventType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
