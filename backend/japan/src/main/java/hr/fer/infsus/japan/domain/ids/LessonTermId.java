package hr.fer.infsus.japan.domain.ids;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonTermId {

    private Long term;

    private Long lesson;

}
