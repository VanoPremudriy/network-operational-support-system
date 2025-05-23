package ru.mirea.network.operational.support.system.info.controller.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mirea.network.operational.support.system.common.api.BaseRs;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.common.dictionary.ErrorCode;
import ru.mirea.network.operational.support.system.info.exception.ClientNotFoundException;

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

        return createRsBuilder("Некорректные данные", ErrorCode.VALIDATION_ERROR_CODE, infos);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseRs validationException(MethodArgumentNotValidException exception) {
        log.error("Ошибка валидации: {}", exception.getMessage());
        Map<String, String> infos = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                infos.put(error.getField(), error.getDefaultMessage()));

        return createRsBuilder("Некорректные данные", ErrorCode.VALIDATION_ERROR_CODE, infos);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public BaseRs validationException(HttpMessageNotReadableException exception) {
        log.error("Ошибка валидации: {}", exception.getMessage());
        if (exception.getCause() instanceof InvalidFormatException invalidFormatException) {
            Map<String, String> infos = new HashMap<>();
            String path = invalidFormatException.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            return createRsBuilder("Некорректные данные", ErrorCode.VALIDATION_ERROR_CODE,
                    Map.of(path, "Неверный формат данных"));
        }

        return createRsBuilder("Некорректные данные", ErrorCode.VALIDATION_ERROR_CODE, exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({ClientNotFoundException.class})
    public BaseRs clientNotFoundException(ClientNotFoundException exception) {
        log.error("Клиент не найден: {}", exception.getMessage());

        return createRsBuilder("Клиент не найден", ErrorCode.CLIENT_NOT_FOUND_ERROR_CODE, exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public BaseRs defaultException(Exception e) {
        log.error("Непредвиденная ошибка: {}", e.getMessage(), e);
        return createRsBuilder("Что-то пошло не так", ErrorCode.UNEXPECTED_ERROR_CODE, "Попробуйте позже");
    }

    private BaseRs createRsBuilder(String title, ErrorCode code, String errorMessage) {

        return createRsBuilder(title, code, Map.of("text", errorMessage));
    }

    private BaseRs createRsBuilder(String title, ErrorCode code, Map<String, String> infos) {

        return BaseRs.builder()
                .success(false)
                .error(ErrorDTO.builder()
                        .title(title)
                        .code(code.getCode())
                        .infos(infos)
                        .build())
                .build();
    }
}
