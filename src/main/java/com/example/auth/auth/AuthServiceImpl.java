
package com.example.auth.auth;

import com.example.auth.auth.dto.LoginRequest;
import com.example.auth.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        // TODO 1: ตรวจสอบ username และ password
        // TODO 2: ตรวจสอบสถานะบัญชีผู้ใช้
        // TODO 3: สร้าง access token
        // TODO 4: จัดการ refresh token และ session
        // TODO 5: สร้าง LoginResponse

        throw new UnsupportedOperationException(
                "Login implementation is not completed"
        );
    }
}
