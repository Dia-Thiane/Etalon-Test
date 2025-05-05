package com.atos.etalonTest.service.Impl;

import com.atos.etalonTest.entity.Role;
import com.atos.etalonTest.repository.RoleRepository;
import com.atos.etalonTest.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        log.info("Retrieving all roles");
        List<Role> roles = roleRepository.findAll();
        log.debug("Number of roles retrieved: {}", roles.size());
        return roles;
    }

    @Override
    public Optional<Role> findById(Long id) {
        log.info("Fetching role by id: {}", id);
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            log.debug("Found role with name: {}", role.get().getName());
        } else {
            log.warn("Role not found with id: {}", id);
        }
        return role;
    }

    @Override
    public Role createRole(Role role) {
        log.info("Creating role with name: {}", role.getName());
        if (roleRepository.existsByName(role.getName())) {
            log.error("Role already exists with name: {}", role.getName());
            throw new IllegalArgumentException("Role already exists");
        }
        Role saved = roleRepository.save(role);
        log.info("Role created successfully; id={}, name={}", saved.getId(), saved.getName());
        return saved;
    }

    @Override
    public Role updateRole(Long id, Role role) {
        log.info("Updating role with id: {}", id);
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Role not found with id {}", id);
                    return new EntityNotFoundException("Role not found");
                });

        existing.setName(role.getName());
        Role updated = roleRepository.save(existing);
        log.info("Role updated successfully; id={}, name={}", updated.getId(), updated.getName());
        return updated;
    }

    @Override
    public void deleteRole(Long id) {
        log.info("Deleting role with id: {}", id);
        if (!roleRepository.existsById(id)) {
            log.error("Role not found with id {}", id);
            throw new EntityNotFoundException("Role not found");
        }
        roleRepository.deleteById(id);
        log.info("Role deleted with id {}", id);
    }
}