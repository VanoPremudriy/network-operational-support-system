package ru.mirea.network.operational.support.system.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.api.login.JwtValidationResponse;
import ru.mirea.network.operational.support.system.auth.dictionary.Constant;
import ru.mirea.network.operational.support.system.auth.exception.UserAlreadyExistException;
import ru.mirea.network.operational.support.system.auth.exception.UserNotFoundException;
import ru.mirea.network.operational.support.system.auth.service.JwtService;

@Slf4j
@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class JwtController {
    private final JwtService jwtService;


    @Operation(summary = "Авторизация пользователя")
    @GetMapping("/validation")
    public JwtValidationResponse validation(@RequestHeader(Constant.HEADER_AUTHORIZATION) String token) {
        return jwtService.parseToken(jwtService.trimPrefix(token));
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({AuthenticationException.class})
    public void handleAuthenticationException(Exception e) {
        log.error("Ошибка аутентификации: {}", e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({UserNotFoundException.class, UserAlreadyExistException.class})
    public void conflictException(Exception e) {
        log.error(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public void defaultException(Exception e) {
        log.error("Непредвиденная ошибка: {}", e.getMessage());
    }
}

