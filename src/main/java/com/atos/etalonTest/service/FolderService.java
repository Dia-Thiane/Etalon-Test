package com.atos.etalonTest.service;

import com.atos.etalonTest.entity.Folder;

import java.util.List;
import java.util.Optional;

public interface FolderService {

    List<Folder> findAll();

    Optional<Folder> findById(Long id);

    Folder createFolder(Folder folder);

    Folder updateFolder(Long id, Folder folder);

    void deleteFolder(Long id);

    List<Folder> findByOwnerId(Long ownerId);
}