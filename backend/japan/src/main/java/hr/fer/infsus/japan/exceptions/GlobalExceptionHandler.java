package hr.fer.infsus.japan.exceptions;

import hr.fer.infsus.japan.dtos.ExceptionDto;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionDto> handleInternalServerError(Exception e) {
        return ResponseEntity.internalServerError().body(
                new ExceptionDto(
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
        );
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ExceptionDto> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = {UniqueException.class})
    public ResponseEntity<ExceptionDto> handleUniqueException(Exception e) {
        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity<>(
                new ExceptionDto(e.getMessage(), status),
                status
        );
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String errorMessage = e.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((msg1, msg2) -> msg1 + " " + msg2)
                .orElse("Validation failed");

        return new ResponseEntity<>(
                new ExceptionDto(errorMessage, status),
                status
        );
    }

}
