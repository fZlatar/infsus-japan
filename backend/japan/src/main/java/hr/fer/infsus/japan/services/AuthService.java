package hr.fer.infsus.japan.services;

import hr.fer.infsus.japan.dtos.AuthInfo;
import hr.fer.infsus.japan.utils.Gender;

import java.sql.Date;

public interface AuthService {

    AuthInfo register(
            String email,
            String name,
            String surname,
            Date dateOfBirth,
            Gender gender,
            String rawPassword
    );

    AuthInfo login(String email, String rawPassword);

    AuthInfo refresh(String refreshToken);

    String accessCookie(String accessToken);

    String refreshCookie(String refreshToken);

}
