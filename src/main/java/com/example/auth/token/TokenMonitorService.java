
package com.example.auth.token;

import com.example.auth.token.dto.TokenStatusResponse;

import java.util.UUID;

public interface TokenMonitorService {

    TokenStatusResponse getTokenStatus(UUID sessionId);
}
