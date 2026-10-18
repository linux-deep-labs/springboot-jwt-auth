package com.example.auth.service.role;

import com.example.auth.dto.role.RoleRequest;
import com.example.auth.dto.role.RoleResponse;
import com.example.auth.dto.role.RoleUpdateRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    RoleResponse getById(Long id);

    List<RoleResponse> getAll();

    RoleResponse update(Long id, RoleUpdateRequest request);

    void delete(Long id);
}