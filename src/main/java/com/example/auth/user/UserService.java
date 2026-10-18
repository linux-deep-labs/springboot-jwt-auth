
package com.example.auth.user;

import com.example.auth.user.dto.UserRequest;
import com.example.auth.user.dto.UserResponse;
import com.example.auth.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse update(Long id, UserUpdateRequest request);

    void delete(Long id);
}
