package hr.fer.infsus.japan.repositories;

import hr.fer.infsus.japan.domain.entities.FileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FileRepository extends CrudRepository<FileEntity, UUID> {
}
