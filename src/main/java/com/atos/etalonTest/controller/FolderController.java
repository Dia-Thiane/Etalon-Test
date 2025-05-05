package com.atos.etalonTest.controller;

import com.atos.etalonTest.entity.Folder;
import com.atos.etalonTest.service.FolderService;
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
@RequestMapping("/api/folders")
@RequiredArgsConstructor
@Validated
public class FolderController {

    private final FolderService folderService;

    @GetMapping
    public ResponseEntity<List<Folder>> allFolders() {
        log.info("Received request GET /api/folders");
        List<Folder> folders = folderService.findAll();
        log.info("Returning {} folders", folders.size());
        return ResponseEntity.ok(folders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Folder> getFolder(@PathVariable Long id) {
        log.info("Received request GET /api/folders/{}", id);
        Optional<Folder> folder = folderService.findById(id);
        if (folder.isEmpty()) {
            log.warn("Folder with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Folder found: name={}", folder.get().getName());
        return ResponseEntity.ok(folder.get());
    }

    @PostMapping
    public ResponseEntity<Folder> createFolder(@Valid @RequestBody Folder folder) {
        log.info("Received request POST /api/folders with folder name: {}", folder.getName());
        Folder created = folderService.createFolder(folder);
        log.info("Folder created with id: {}", created.getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable Long id,
                                               @Valid @RequestBody Folder folder) {
        log.info("Received request PUT /api/folders/{} for update", id);
        Folder updated = folderService.updateFolder(id, folder);
        log.info("Folder updated with id: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        log.info("Received request DELETE /api/folders/{}", id);
        folderService.deleteFolder(id);
        log.info("Folder deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Folder>> getFoldersByOwner(@PathVariable Long ownerId) {
        log.info("Received request GET /api/folders/owner/{}", ownerId);
        List<Folder> folders = folderService.findByOwnerId(ownerId);
        log.info("Returning {} folders for owner id {}", folders.size(), ownerId);
        return ResponseEntity.ok(folders);
    }
}