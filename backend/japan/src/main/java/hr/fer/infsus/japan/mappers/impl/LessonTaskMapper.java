package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.dto.LessonTaskDto;
import hr.fer.infsus.japan.domain.entities.LessonTaskEntity;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonTaskMapper implements Mapper<LessonTaskEntity, LessonTaskDto> {

    private final ModelMapper modelMapper;

    @Override
    public LessonTaskDto toDto(LessonTaskEntity entity) {
        return modelMapper.map(entity, LessonTaskDto.class);
    }

    @Override
    public LessonTaskEntity toEntity(LessonTaskDto dto) {
        return modelMapper.map(dto, LessonTaskEntity.class);
    }

}
