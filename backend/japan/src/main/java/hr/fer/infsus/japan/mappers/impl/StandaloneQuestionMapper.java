package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.entities.StandaloneQuestionEntity;
import hr.fer.infsus.japan.domain.dto.StandaloneQuestionDto;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StandaloneQuestionMapper implements Mapper<StandaloneQuestionEntity, StandaloneQuestionDto> {

    private final ModelMapper modelMapper;

    @Override
    public StandaloneQuestionDto toDto(StandaloneQuestionEntity entity) {
        return modelMapper.map(entity, StandaloneQuestionDto.class);
    }

    @Override
    public StandaloneQuestionEntity toEntity(StandaloneQuestionDto dto) {
        return modelMapper.map(dto, StandaloneQuestionEntity.class);
    }

}
