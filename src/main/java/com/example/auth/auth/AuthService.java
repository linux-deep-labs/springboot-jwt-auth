
package com.example.auth.auth;

import com.example.auth.auth.dto.LoginRequest;
import com.example.auth.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
