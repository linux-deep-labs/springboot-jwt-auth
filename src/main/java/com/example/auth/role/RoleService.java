package com.example.auth.role;

import com.example.auth.role.dto.RoleRequest;
import com.example.auth.role.dto.RoleResponse;
import com.example.auth.role.dto.RoleUpdateRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    RoleResponse getById(Long id);

    List<RoleResponse> getAll();

    RoleResponse update(Long id, RoleUpdateRequest request);

    void delete(Long id);
}