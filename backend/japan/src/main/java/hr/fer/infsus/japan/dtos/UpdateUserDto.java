package hr.fer.infsus.japan.dtos;

import hr.fer.infsus.japan.utils.Gender;
import hr.fer.infsus.japan.utils.ThemeColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.sql.Date;

@Data
public class UpdateUserDto {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Surname cannot be blank.")
    private String surname;

    @NotNull(message = "Gender cannot be null.")
    private Gender gender;

    @Past(message = "Date of birth must be in past.")
    @NotNull(message = "Date of birth cannot be null.")
    private Date dateOfBirth;

    @NotNull(message = "Theme color cannot be null.")
    private ThemeColor themeColor;

    @NotBlank(message = "Date format cannot be blank.")
    private String dateFormat;

}
