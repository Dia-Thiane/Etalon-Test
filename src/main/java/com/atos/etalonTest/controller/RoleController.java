package com.atos.etalonTest.controller;

import com.atos.etalonTest.entity.Role;
import com.atos.etalonTest.service.RoleService;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Validated

public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> allRoles() {
        log.info("Received request GET /api/roles");
        List<Role> roles = roleService.findAll();
        log.info("Returning {} roles", roles.size());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        log.info("Received request GET /api/roles/{}", id);
        Optional<Role> role = roleService.findById(id);
        if (role.isEmpty()) {
            log.warn("Role with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Role found: name={}", role.get().getName());
        return ResponseEntity.ok(role.get());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        log.info("Received request POST /api/roles with name: {}", role.getName());
        Role created = roleService.createRole(role);
        log.info("Role created with id: {}", created.getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id,
                                           @Valid @RequestBody Role role) {
        log.info("Received request PUT /api/roles/{} for update", id);
        Role updated = roleService.updateRole(id, role);
        log.info("Role updated with id: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.info("Received request DELETE /api/roles/{}", id);
        roleService.deleteRole(id);
        log.info("Role deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}