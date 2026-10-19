
package com.example.auth.session;

import com.example.auth.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository
        extends JpaRepository<UserSession, UUID> {

    List<UserSession> findAllByUser_Id(Long userId);

    Optional<UserSession> findByIdAndUser_Id(
            UUID sessionId,
            Long userId
    );
}
