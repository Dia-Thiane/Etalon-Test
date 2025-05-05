package com.atos.etalonTest.service.Impl;

import com.atos.etalonTest.entity.User;
import com.atos.etalonTest.repository.UserRepository;
import com.atos.etalonTest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        log.debug("Number of users retrieved: {}", users.size());
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Retrieving user by id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.debug("Found user with username: {}", user.get().getUsername());
        } else {
            log.warn("User with id {} not found", id);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        log.info("Creating user with username: {}", user.getUsername());
        if (userRepository.existsByUsername(user.getUsername())) {
            log.error("Username '{}' already exists", user.getUsername());
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error("Email '{}' already exists", user.getEmail());
            throw new IllegalArgumentException("Email is already used");
        }
        User savedUser = userRepository.save(user);
        log.info("User created successfully; id={}, username={}", savedUser.getId(), savedUser.getUsername());
        return savedUser;
    }

    @Override
    public User updateUser(Long id, User user) {
        log.info("Updating user with id: {}", id);
        User existing = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", id);
                    return new EntityNotFoundException("User not found");
                });

        log.debug("Updating fields for user id {}", id);
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setRoles(user.getRoles());
        User updated = userRepository.save(existing);
        log.info("User updated successfully; id={}, username={}", updated.getId(), updated.getUsername());
        return updated;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            log.error("User not found with id {}", id);
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
        log.info("User deleted with id {}", id);
    }
}