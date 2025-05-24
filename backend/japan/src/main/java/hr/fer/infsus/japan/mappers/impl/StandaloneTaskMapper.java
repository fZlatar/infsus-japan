package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.dto.StandaloneTaskDto;
import hr.fer.infsus.japan.domain.entities.StandaloneTaskEntity;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StandaloneTaskMapper implements Mapper<StandaloneTaskEntity, StandaloneTaskDto> {

    private final ModelMapper modelMapper;

    @Override
    public StandaloneTaskDto toDto(StandaloneTaskEntity entity) {
        return modelMapper.map(entity, StandaloneTaskDto.class);
    }

    @Override
    public StandaloneTaskEntity toEntity(StandaloneTaskDto dto) {
        return modelMapper.map(dto, StandaloneTaskEntity.class);
    }
}
