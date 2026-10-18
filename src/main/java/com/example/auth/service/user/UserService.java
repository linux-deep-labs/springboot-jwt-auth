
package com.example.auth.service.user;

import com.example.auth.dto.user.UserRequest;
import com.example.auth.dto.user.UserResponse;
import com.example.auth.dto.user.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse update(Long id, UserUpdateRequest request);

    void delete(Long id);
}
