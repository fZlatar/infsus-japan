package hr.fer.infsus.japan.domain.dto;

import hr.fer.infsus.japan.utils.Gender;
import hr.fer.infsus.japan.utils.ThemeColor;
import lombok.Data;

import java.sql.Date;

@Data
public class UserDto {

    private String email;

    private String name;

    private String surname;

    private Date dateOfBirth;

    private Gender gender;

    private ThemeColor themeColor;

    private String dateFormat;

}
