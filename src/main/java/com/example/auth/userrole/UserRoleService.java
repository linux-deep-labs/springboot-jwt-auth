
package com.example.auth.userrole;

import com.example.auth.entity.UserRole;

import java.util.List;

public interface UserRoleService {

    UserRole assignRole(Long userId, Long roleId);

    void removeRole(Long userId, Long roleId);

    List<UserRole> getRolesByUserId(Long userId);
}
