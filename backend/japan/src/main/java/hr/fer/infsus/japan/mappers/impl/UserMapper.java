package hr.fer.infsus.japan.mappers.impl;

import hr.fer.infsus.japan.domain.entities.UserEntity;
import hr.fer.infsus.japan.domain.dto.UserDto;
import hr.fer.infsus.japan.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    @Override
    public UserDto toDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        return modelMapper.map(dto, UserEntity.class);
    }
}
