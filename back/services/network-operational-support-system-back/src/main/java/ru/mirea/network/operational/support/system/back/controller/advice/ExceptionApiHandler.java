package ru.mirea.network.operational.support.system.back.controller.advice;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;
import ru.mirea.network.operational.support.system.api.dto.ErrorDTO;

@RestControllerAdvice
public class ExceptionApiHandler {
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({ValidationException.class})
//    public CreateRouteRs validationException(ValidationException exception) {
//        return CreateRouteRs.builder()
//                .success(false)
//                .error(ErrorDTO.builder()
//                        .title("Ошибка валидации")
//                        .text(exception.getMessage())
//                        .build())
//                .build();
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({Exception.class})
//    public CreateRouteRs defaultException(Exception exception) {
//        return CreateRouteRs.builder()
//                .success(false)
//                .error(ErrorDTO.builder()
//                        .code(0)
//                        .title("Непредвиденная ошибка")
//                        .text(exception.getMessage())
//                        .build())
//                .build();
//    }
}
