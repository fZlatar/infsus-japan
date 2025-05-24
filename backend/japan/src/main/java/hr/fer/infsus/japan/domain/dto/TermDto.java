package hr.fer.infsus.japan.domain.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
public class TermDto {

    private Long id;

    private Integer difficulty;

    private String termNameCro;

    private String termNameJpn;

    private String descriptionCro;

    private String descriptionJpn;

    @Nullable
    private UUID audioUuid;

    @Nullable
    private UUID imageFile;

}
