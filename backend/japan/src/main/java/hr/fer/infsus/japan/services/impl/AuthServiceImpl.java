package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.domain.entities.UserEntity;
import hr.fer.infsus.japan.dtos.AuthInfo;
import hr.fer.infsus.japan.services.AuthService;
import hr.fer.infsus.japan.services.JwtService;
import hr.fer.infsus.japan.services.UserService;
import hr.fer.infsus.japan.utils.Gender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${application.backend.domain}")
    private String backendDomain;

    @Value("${application.security.jwt.access.expiration}")
    private long accessExpiration;

    @Value("${application.security.jwt.refresh.expiration}")
    private long refreshExpiration;

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            @Lazy final UserService userService,
            @Lazy final JwtService jwtService,
            @Lazy final PasswordEncoder passwordEncoder,
            @Lazy final AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AuthInfo register(
            String email,
            String name,
            String surname,
            Date dateOfBirth,
            Gender gender,
            String rawPassword
    ) {
        userService.register(
                email,
                name,
                surname,
                dateOfBirth,
                gender,
                passwordEncoder.encode(rawPassword)
        );

        return new AuthInfo(
                jwtService.generateAccess(email),
                jwtService.generateRefresh(email),
                email
        );
    }

    @Override
    public AuthInfo login(String email, String rawPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, rawPassword)
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return new AuthInfo(
                jwtService.generateAccess(user.getEmail()),
                jwtService.generateRefresh(user.getEmail()),
                user.getEmail()
        );
    }

    @Override
    public AuthInfo refresh(String refreshToken) {
        String email = jwtService.extractSubjectFromRefresh(refreshToken);
        UserEntity user = userService.findByEmailThrow(email);

        return new AuthInfo(
                jwtService.generateAccess(user.getEmail()),
                jwtService.generateRefresh(user.getEmail()),
                user.getEmail()
        );
    }

    @Override
    public String accessCookie(String accessToken) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken")
                .httpOnly(true)
                .secure(true)
                .domain(backendDomain)
                .path("/")
                .sameSite("Strict")
                .maxAge(accessExpiration / 1000)
                .build();
        return accessCookie + "; Priority=High";
    }

    @Override
    public String refreshCookie(String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken")
                .httpOnly(true)
                .secure(true)
                .domain(backendDomain)
                .path("/auth")
                .sameSite("Strict")
                .maxAge(refreshExpiration / 1000)
                .build();
        return refreshCookie + "; Priority=High";
    }

}
