package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.dto.TermDto;
import hr.fer.infsus.japan.domain.entities.TermEntity;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermMapper implements Mapper<TermEntity, TermDto> {

    private final ModelMapper modelMapper;

    @Override
    public TermDto toDto(TermEntity entity) {
        return modelMapper.map(entity, TermDto.class);
    }

    @Override
    public TermEntity toEntity(TermDto dto) {
        return modelMapper.map(dto, TermEntity.class);
    }
}
