package com.example.week6.controller;

import com.example.week6.dto.ApiResponse;
import com.example.week6.dto.UserRequest;
import com.example.week6.entity.User;
import com.example.week6.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserRequest req) {
        logger.info("Creating user: {}", req.getEmail());

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse<>(true, "User created", user));
    }
}
