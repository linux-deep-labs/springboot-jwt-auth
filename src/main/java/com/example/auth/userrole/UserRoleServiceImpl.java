
package com.example.auth.userrole;

import com.example.auth.entity.Role;
import com.example.auth.entity.User;
import com.example.auth.entity.UserRole;
import com.example.auth.entity.UserRoleId;
import com.example.auth.role.RoleRepository;
import com.example.auth.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleServiceImpl(
            UserRoleRepository userRoleRepository,
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserRole assignRole(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Role not found"));

        if (userRoleRepository.existsByUser_IdAndRole_Id(
                userId, roleId)) {
            throw new IllegalStateException(
                    "User already has this role");
        }

        UserRole userRole = new UserRole();

        UserRoleId id = new UserRoleId();
        id.setUserId(userId);
        id.setRoleId(roleId);

        userRole.setId(id);
        userRole.setUser(user);
        userRole.setRole(role);

        return userRoleRepository.save(userRole);
    }

    @Override
    @Transactional
    public void removeRole(Long userId, Long roleId) {

        UserRole userRole = userRoleRepository
                .findByUser_IdAndRole_Id(userId, roleId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User role assignment not found"));

        userRoleRepository.delete(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRole> getRolesByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        return userRoleRepository.findAllByUser_Id(userId);
    }
}
