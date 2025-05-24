package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.domain.entities.UserEntity;
import hr.fer.infsus.japan.dtos.UpdateUserDto;
import hr.fer.infsus.japan.dtos.UserDto;
import hr.fer.infsus.japan.mappers.impl.UserMapper;
import hr.fer.infsus.japan.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("#email == authentication.name")
    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable("email") String email) {
        UserEntity user = userService.findByEmailThrow(email);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PreAuthorize("#email == authentication.name")
    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("email") String email,
            @Valid @RequestBody UpdateUserDto request
    ) {
        UserEntity user = userService.update(
                email,
                request.getName(),
                request.getSurname(),
                request.getDateOfBirth(),
                request.getGender(),
                request.getThemeColor(),
                request.getDateFormat()
        );

        return ResponseEntity.ok(userMapper.toDto(user));
    }

}
