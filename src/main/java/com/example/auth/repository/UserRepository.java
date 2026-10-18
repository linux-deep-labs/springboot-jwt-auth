
package com.example.auth.repository;

import com.example.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ตรวจสอบ username ซ้ำ
    boolean existsByUsername(String username);

    // ตรวจสอบ email ซ้ำ
    boolean existsByEmail(String email);

    // ค้นหาด้วย username
    Optional<User> findByUsername(String username);

    // ค้นหาด้วย email
    Optional<User> findByEmail(String email);
}
