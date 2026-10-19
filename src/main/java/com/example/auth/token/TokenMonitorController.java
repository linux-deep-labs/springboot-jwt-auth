
package com.example.auth.token;

import com.example.auth.token.dto.TokenStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tokens")
public class TokenMonitorController {

    private final TokenMonitorService tokenMonitorService;

    public TokenMonitorController(
            TokenMonitorService tokenMonitorService) {
        this.tokenMonitorService = tokenMonitorService;
    }

    @GetMapping("/{sessionId}/status")
    public ResponseEntity<TokenStatusResponse> getTokenStatus(
            @PathVariable UUID sessionId) {

        TokenStatusResponse response =
                tokenMonitorService.getTokenStatus(sessionId);

        return ResponseEntity.ok(response);
    }
}
