package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.dto.ProgressDto;
import hr.fer.infsus.japan.domain.entities.ProgressEntity;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgressMapper implements Mapper<ProgressEntity, ProgressDto> {

    private final ModelMapper modelMapper;

    @Override
    public ProgressDto toDto(ProgressEntity progressEntity) {
        return modelMapper.map(progressEntity, ProgressDto.class);
    }

    @Override
    public ProgressEntity toEntity(ProgressDto dto) {
        return modelMapper.map(dto, ProgressEntity.class);
    }
}
