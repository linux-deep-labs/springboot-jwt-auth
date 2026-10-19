package com.example.auth.audit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditLogService {

    private final AuditEventRepository auditEventRepository;

    public AuditLogService(
            AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    @Transactional
    public void log(AuditEvent event) {
        auditEventRepository.save(event);
    }
}