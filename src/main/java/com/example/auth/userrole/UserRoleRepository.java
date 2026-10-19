
package com.example.auth.userrole;

import com.example.auth.entity.UserRole;
import com.example.auth.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository
        extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findAllByUser_Id(Long userId);

    Optional<UserRole> findByUser_IdAndRole_Id(
            Long userId,
            Long roleId
    );

    boolean existsByUser_IdAndRole_Id(
            Long userId,
            Long roleId
    );

    void deleteByUser_IdAndRole_Id(
            Long userId,
            Long roleId
    );
}
