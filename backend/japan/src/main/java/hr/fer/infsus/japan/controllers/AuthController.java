package hr.fer.infsus.japan.controllers;

import hr.fer.infsus.japan.dtos.AuthInfo;
import hr.fer.infsus.japan.dtos.LoginDto;
import hr.fer.infsus.japan.dtos.RegisterDto;
import hr.fer.infsus.japan.services.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto request) {
        AuthInfo authInfo = authService.register(
                request.getEmail(),
                request.getName(),
                request.getSurname(),
                request.getDateOfBirth(),
                request.getGender(),
                request.getRawPassword()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{email}")
                .buildAndExpand(authInfo.email())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authService.accessCookie(authInfo.access()));
        headers.add(HttpHeaders.SET_COOKIE, authService.refreshCookie(authInfo.refresh()));
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).build();
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginDto request
    ) {
        AuthInfo authInfo = authService.login(request.getEmail(), request.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authService.accessCookie(authInfo.access()));
        headers.add(HttpHeaders.SET_COOKIE, authService.refreshCookie(authInfo.refresh()));

        return ResponseEntity.ok().headers(headers).build();
    }

    @PermitAll
    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ) {
        AuthInfo authInfo = authService.refresh(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authService.accessCookie(authInfo.access()));
        headers.add(HttpHeaders.SET_COOKIE, authService.refreshCookie(authInfo.refresh()));

        return ResponseEntity.ok().headers(headers).build();
    }

}
