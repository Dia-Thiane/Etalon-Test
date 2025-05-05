package com.atos.etalonTest.service.Impl;

import com.atos.etalonTest.entity.Folder;
import com.atos.etalonTest.entity.User;
import com.atos.etalonTest.repository.FolderRepository;
import com.atos.etalonTest.repository.UserRepository;
import com.atos.etalonTest.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Override
    public List<Folder> findAll() {
        log.info("Retrieving all folders");
        List<Folder> folders = folderRepository.findAll();
        log.debug("Total folders retrieved: {}", folders.size());
        return folders;
    }

    @Override
    public Optional<Folder> findById(Long id) {
        log.info("Finding folder with id: {}", id);
        Optional<Folder> folder = folderRepository.findById(id);
        if (folder.isPresent()) {
            log.debug("Folder found with name: {}", folder.get().getName());
        } else {
            log.warn("Folder not found with id: {}", id);
        }
        return folder;
    }

    @Override
    public Folder createFolder(Folder folder) {
        Long ownerId = folder.getOwner() != null ? folder.getOwner().getId() : null;
        log.info("Creating folder '{}' for owner id: {}", folder.getName(), ownerId);
        if (ownerId == null) {
            log.error("Folder creation failed: Owner is null");
            throw new IllegalArgumentException("Folder owner must be specified");
        }
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> {
                    log.error("Owner user not found with id {}", ownerId);
                    return new EntityNotFoundException("Owner user not found");
                });

        folder.setOwner(owner);
        Folder saved = folderRepository.save(folder);
        log.info("Folder created successfully; id={}, name={}", saved.getId(), saved.getName());
        return saved;
    }

    @Override
    public Folder updateFolder(Long id, Folder folder) {
        log.info("Updating folder with id: {}", id);
        Folder existing = folderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Folder not found with id {}", id);
                    return new EntityNotFoundException("Folder not found");
                });

        existing.setName(folder.getName());

        if (folder.getOwner() != null) {
            Long ownerId = folder.getOwner().getId();
            User owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> {
                        log.error("Owner user not found with id {}", ownerId);
                        return new EntityNotFoundException("Owner user not found");
                    });
            existing.setOwner(owner);
        }
        Folder updated = folderRepository.save(existing);
        log.info("Folder updated successfully; id={}, name={}", updated.getId(), updated.getName());
        return updated;
    }

    @Override
    public void deleteFolder(Long id) {
        log.info("Deleting folder with id: {}", id);
        if (!folderRepository.existsById(id)) {
            log.error("Folder not found with id {}", id);
            throw new EntityNotFoundException("Folder not found");
        }
        folderRepository.deleteById(id);
        log.info("Folder deleted with id {}", id);
    }

    @Override
    public List<Folder> findByOwnerId(Long ownerId) {
        log.info("Finding folders by owner id: {}", ownerId);
        List<Folder> folders = folderRepository.findByOwnerId(ownerId);
        log.debug("Folders found: {}", folders.size());
        return folders;
    }
}