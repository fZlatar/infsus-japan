package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.entities.LessonQuestionEntity;
import hr.fer.infsus.japan.domain.dto.LessonQuestionDto;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonQuestionMapper implements Mapper<LessonQuestionEntity, LessonQuestionDto> {

    private final ModelMapper modelMapper;

    @Override
    public LessonQuestionDto toDto(LessonQuestionEntity entity) {
        return modelMapper.map(entity, LessonQuestionDto.class);
    }

    @Override
    public LessonQuestionEntity toEntity(LessonQuestionDto dto) {
        return modelMapper.map(dto, LessonQuestionEntity.class);
    }

}
