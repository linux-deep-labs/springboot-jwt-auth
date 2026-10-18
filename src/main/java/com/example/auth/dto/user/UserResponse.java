
package com.example.auth.dto.user;

import com.example.auth.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String status;

    private List<String> roles;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setStatus(user.getStatus());

        response.setRoles(
                user.getRoles()
                        .stream()
                        .map(role -> role.getName())
                        .toList()
        );

        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}
