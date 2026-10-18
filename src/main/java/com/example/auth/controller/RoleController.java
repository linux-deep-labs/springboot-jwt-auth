
package com.example.auth.controller;

import com.example.auth.dto.role.RoleRequest;
import com.example.auth.dto.role.RoleResponse;
import com.example.auth.dto.role.RoleUpdateRequest;
import com.example.auth.service.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(
            @Valid @RequestBody RoleRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.create(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateRequest request) {

        return ResponseEntity.ok(
                roleService.update(id, request)
        );
    }

}
