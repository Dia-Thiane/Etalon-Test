package com.atos.etalonTest.controller;


import com.atos.etalonTest.entity.User;
import com.atos.etalonTest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated

public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        log.info("Received request GET /api/users");
        List<User> users = userService.findAll();
        log.info("Returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.info("Received request GET /api/users/{}", id);
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            log.warn("User with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("User found: username={}", user.get().getUsername());
        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("Received request POST /api/users with username: {}", user.getUsername());
        User created = userService.createUser(user);
        log.info("User created with id: {}", created.getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @Valid @RequestBody User user) {
        log.info("Received request PUT /api/users/{} for update", id);
        User updated = userService.updateUser(id, user);
        log.info("User updated with id: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Received request DELETE /api/users/{}", id);
        userService.deleteUser(id);
        log.info("User deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}