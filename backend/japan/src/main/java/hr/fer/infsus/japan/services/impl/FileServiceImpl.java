package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.FileEntity;
import hr.fer.infsus.japan.exceptions.NotFoundException;
import hr.fer.infsus.japan.repositories.FileRepository;
import hr.fer.infsus.japan.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public FileEntity findByUuidThrow(UUID uuid) {
        return fileRepository.findById(uuid).orElseThrow(NotFoundException::new);
    }

}
