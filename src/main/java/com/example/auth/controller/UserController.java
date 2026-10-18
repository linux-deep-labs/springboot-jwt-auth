
package com.example.auth.controller;

import com.example.auth.dto.user.UserRequest;
import com.example.auth.dto.user.UserResponse;
import com.example.auth.dto.user.UserUpdateRequest;
import com.example.auth.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // CREATE USER
    @PostMapping
    public ResponseEntity<UserResponse> create(
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {

        return ResponseEntity.ok(
                userService.findAll()
        );
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.findById(id)
        );
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        return ResponseEntity.ok(
                userService.update(id, request)
        );
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
