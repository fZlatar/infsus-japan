package hr.fer.infsus.japan.domain.ids;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressId implements Serializable {

    private Long lesson;

    private String user;

}
