
package com.example.auth.service.user;

import com.example.auth.dto.user.UserRequest;
import com.example.auth.dto.user.UserResponse;
import com.example.auth.dto.user.UserUpdateRequest;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        if (request.getEmail() != null
                && !request.getEmail().isBlank()
                && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        OffsetDateTime now = OffsetDateTime.now();

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        user.setStatus("ACTIVE");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return UserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return UserResponse.from(getUserById(id));
    }

    @Override
    public UserResponse update(
            Long id,
            UserUpdateRequest request
    ) {
        User user = getUserById(id);

        if (!user.getUsername().equals(request.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        if (request.getEmail() != null
                && !request.getEmail().isBlank()
                && !request.getEmail().equals(user.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setUpdatedAt(OffsetDateTime.now());

        return UserResponse.from(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }
}
