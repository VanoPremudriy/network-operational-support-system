package ru.mirea.network.operational.support.system.auth.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mirea.network.operational.support.system.auth.exception.UserAlreadyExistException;
import ru.mirea.network.operational.support.system.auth.exception.UserNotFoundException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

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
