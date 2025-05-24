package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.entities.LessonEntity;
import hr.fer.infsus.japan.domain.dto.LessonDto;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonMapper implements Mapper<LessonEntity, LessonDto> {

    private final ModelMapper modelMapper;

    @Override
    public LessonDto toDto(LessonEntity lessonEntity) {
        return modelMapper.map(lessonEntity, LessonDto.class);
    }

    @Override
    public LessonEntity toEntity(LessonDto dto) {
        return modelMapper.map(dto, LessonEntity.class);
    }

}
