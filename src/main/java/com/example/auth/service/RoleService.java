package com.example.auth.service;

import com.example.auth.dto.role.RoleRequest;
import com.example.auth.dto.role.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    RoleResponse getById(Long id);

    List<RoleResponse> getAll();

    RoleResponse update(Long id, RoleRequest request);

    void delete(Long id);
}