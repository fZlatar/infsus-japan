package hr.fer.infsus.japan.dtos;

import hr.fer.infsus.japan.utils.DateFormat;
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

    private String password;

    private ThemeColor themeColor;

    private DateFormat dateFormat;

}
