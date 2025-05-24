package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.UserEntity;
import hr.fer.infsus.japan.exceptions.NotFoundException;
import hr.fer.infsus.japan.exceptions.UniqueException;
import hr.fer.infsus.japan.repositories.UserRepository;
import hr.fer.infsus.japan.services.UserService;
import hr.fer.infsus.japan.utils.Gender;
import hr.fer.infsus.japan.utils.ThemeColor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findByEmailThrow(String email) {
        return userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(
            String email,
            String name,
            String surname,
            Date dateOfBirth,
            Gender gender,
            String encodedPassword
    ) {
        if (userRepository.existsByEmail(email)) {
            throw new UniqueException("Email already exists.");
        }

        UserEntity user = UserEntity.builder()
                .email(email)
                .name(name)
                .surname(surname)
                .dateOfBirth(dateOfBirth)
                .gender(gender)
                .password(encodedPassword)
                .themeColor(ThemeColor.SYSTEM)
                .dateFormat("DD.MM.YYYY")
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity update(
            String email,
            String name,
            String surname,
            Date dateOfBirth,
            Gender gender,
            ThemeColor themeColor,
            String dateFormat
    ) {
        UserEntity user = findByEmailThrow(email);

        user.setName(name);
        user.setSurname(surname);
        user.setDateOfBirth(dateOfBirth);
        user.setGender(gender);
        user.setThemeColor(themeColor);
        user.setDateFormat(dateFormat);

        return userRepository.save(user);
    }

}
