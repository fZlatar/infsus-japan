package hr.fer.infsus.japan.exceptions;

import hr.fer.infsus.japan.dtos.ExceptionDto;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .orElse("Validation failed.");

        return new ResponseEntity<>(
                new ExceptionDto(errorMessage, status),
                status
        );
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException() {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(
                new ExceptionDto(
                        "Bad credentials.",
                        unauthorized
                ),
                unauthorized
        );
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException() {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(
                authentication == null ||
                        !authentication.isAuthenticated() ||
                        authentication.getPrincipal().equals("anonymousUser")
        ) {
            return new ResponseEntity<>(
                    new ExceptionDto("Unauthorized.", unauthorized),
                    unauthorized
            );
        }
        return new ResponseEntity<>(
                new ExceptionDto("Access denied.", forbidden),
                forbidden
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
            new ExceptionDto(e.getMessage(), HttpStatus.BAD_REQUEST),
            HttpStatus.BAD_REQUEST
        );
    }

}
