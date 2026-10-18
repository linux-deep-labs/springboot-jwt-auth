
package com.example.auth.service.role;

import com.example.auth.dto.role.RoleRequest;
import com.example.auth.dto.role.RoleResponse;
import com.example.auth.dto.role.RoleUpdateRequest;
import com.example.auth.entity.Role;
import com.example.auth.exception.DuplicateResourceException;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.repository.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleResponse create(RoleRequest request) {

        String name = request.name().trim();

        if (roleRepository.existsByName(name)) {
            throw new DuplicateResourceException(
                    "Role already exists: " + name
            );
        }

        Role role = new Role();
        role.setName(name);
        role.setDescription(request.description());

        Role savedRole = roleRepository.save(role);

        return toResponse(savedRole);
    }

    @Override
    public RoleResponse getById(Long id) {
        Role role = findRoleById(id);
        return toResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public RoleResponse update(Long id, @Valid RoleUpdateRequest request) {

        Role role = findRoleById(id);
        String name = request.name().trim();

        boolean duplicateName = roleRepository
                .existsByName(name)
                && !role.getName().equals(name);

        if (duplicateName) {
            throw new DuplicateResourceException(
                    "Role already exists: " + name
            );
        }

        role.setName(name);
        role.setDescription(request.description());

        Role updatedRole = roleRepository.save(role);

        return toResponse(updatedRole);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role role = findRoleById(id);
        roleRepository.delete(role);
    }

    private Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found with id: " + id
                        )
                );
    }

    private RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getCreatedAt(),
                role.getUpdatedAt()
        );
    }
}
