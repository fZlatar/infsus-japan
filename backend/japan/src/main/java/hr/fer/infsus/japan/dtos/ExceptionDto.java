package hr.fer.infsus.japan.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ExceptionDto {

    private String message;

    private HttpStatus status;

    private Integer statusCode;

    public ExceptionDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.statusCode = status.value();
    }

}
