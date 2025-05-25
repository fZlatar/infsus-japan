package hr.fer.infsus.japan.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProgressDto {

    @NotNull(message = "Term number cannot be null.")
    Integer termNum;

    Integer questionNum;

}
