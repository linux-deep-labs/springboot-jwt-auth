
package com.example.auth.session;

import com.example.auth.session.dto.SessionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getMySessions(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // Replace this with the authenticated user's database ID.
        throw new UnsupportedOperationException(
                "Resolve authenticated user ID before use"
        );
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> revokeSession(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID sessionId
    ) {
        // Resolve the authenticated user's database ID,
        // then call sessionService.revokeSession(userId, sessionId).
        throw new UnsupportedOperationException(
                "Resolve authenticated user ID before use"
        );
    }
}
