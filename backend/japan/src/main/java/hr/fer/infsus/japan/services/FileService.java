package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.domain.entities.FileEntity;

import java.util.UUID;

public interface FileService {

    FileEntity findByUuidThrow(UUID uuid);

}
