package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.dto.LessonTermDto;
import hr.fer.infsus.japan.domain.entities.LessonTermEntity;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonTermMapper implements Mapper<LessonTermEntity, LessonTermDto> {

    private final ModelMapper modelMapper;

    @Override
    public LessonTermDto toDto(LessonTermEntity entity) {
        return modelMapper.map(entity, LessonTermDto.class);
    }

    @Override
    public LessonTermEntity toEntity(LessonTermDto dto) {
        return modelMapper.map(dto, LessonTermEntity.class);
    }

}
