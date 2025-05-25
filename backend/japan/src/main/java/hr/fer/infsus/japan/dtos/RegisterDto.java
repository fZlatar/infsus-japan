package hr.fer.infsus.japan.dtos;

import hr.fer.infsus.japan.utils.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.sql.Date;

@Data
public class RegisterDto {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email.", regexp = "^[\\-A-Za-z0-9._%+]+@[\\-A-Za-z0-9.]+\\.[A-Za-z]{2,}$")
    private String email;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Surname cannot be blank.")
    private String surname;

    @NotNull(message = "Gender cannot be null.")
    private Gender gender;

    @Past(message = "Date of birth must be in past.")
    @NotNull(message = "Date of birth cannot be null.")
    private Date dateOfBirth;

    @NotBlank(message = "Password cannot be blank.")
    private String rawPassword;

}
