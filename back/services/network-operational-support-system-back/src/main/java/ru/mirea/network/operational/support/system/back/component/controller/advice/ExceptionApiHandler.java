package ru.mirea.network.operational.support.system.back.component.controller.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mirea.network.operational.support.system.common.api.BaseRs;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({ConstraintViolationException.class})
    public BaseRs validationException(ConstraintViolationException exception) {
        log.error("Ошибка валидации: {}", exception.getMessage());

        Map<String, String> infos = exception.getConstraintViolations().stream()
                .map(violation -> Map.entry(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return createRsBuilder("Некорректные данные", "VALIDATION:ERROR", infos);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseRs validationException(MethodArgumentNotValidException exception) {
        log.error("Ошибка валидации: {}", exception.getMessage());
        Map<String, String> infos = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                infos.put(error.getField(), error.getDefaultMessage()));

        return createRsBuilder("Некорректные данные", "VALIDATION:ERROR", infos);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public BaseRs defaultException(Exception e) {
        log.error("Непредвиденная ошибка: {}", e.getMessage());
        return createRsBuilder("Что-то пошло не так", "UNEXPECTED:ERROR", "Попробуйте позже");
    }

    private BaseRs createRsBuilder(String title, String code, String errorMessage) {

        return createRsBuilder(title, code, Map.of("text", errorMessage));
    }

    private BaseRs createRsBuilder(String title, String code, Map<String, String> infos) {

        return BaseRs.builder()
                .success(false)
                .error(ErrorDTO.builder()
                        .title(title)
                        .code(code)
                        .infos(infos)
                        .build())
                .build();
    }
}
