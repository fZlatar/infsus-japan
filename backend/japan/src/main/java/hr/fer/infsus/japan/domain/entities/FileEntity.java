package hr.fer.infsus.japan.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity(name = "file")
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UUID", nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] data;

    @Column(name = "media_type", nullable = false, length = 50)
    private String mediaType;

    @Column(name = "file_name", nullable = false, length = 50)
    private String fileName;

}
