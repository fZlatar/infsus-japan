package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.domain.entities.UserEntity;
import hr.fer.infsus.japan.utils.DateFormat;
import hr.fer.infsus.japan.utils.Gender;
import hr.fer.infsus.japan.utils.ThemeColor;

import java.sql.Date;

public interface UserService {

    UserEntity findByEmailThrow(String email);

    void register(
            String email,
            String name,
            String surname,
            Date dateOfBirth,
            Gender gender,
            String encodedPassword
    );

    UserEntity update(
            String email,
            String name,
            String surname,
            Date dateOfBirth,
            Gender gender,
            ThemeColor themeColor,
            DateFormat dateFormat
    );

}
