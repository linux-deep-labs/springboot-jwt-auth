package com.example.auth.token;

import com.example.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}
