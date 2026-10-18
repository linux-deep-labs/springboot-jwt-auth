
package com.example.auth.repository;

import com.example.auth.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepository
        extends JpaRepository<UserSession, UUID> {

    List<UserSession> findAllByUserId(UUID userId);
}
