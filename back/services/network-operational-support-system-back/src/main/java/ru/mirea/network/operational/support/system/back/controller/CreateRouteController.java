package ru.mirea.network.operational.support.system.back.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRq;
import ru.mirea.network.operational.support.system.api.create.route.CreateRouteRs;
import ru.mirea.network.operational.support.system.api.dto.ErrorDTO;
import ru.mirea.network.operational.support.system.back.service.CreateRouteService;

import static ru.mirea.network.operational.support.system.back.dictionary.Constant.CREATE_ROUTE_ENDPOINT;

@Validated
@RestController
@RequiredArgsConstructor
public class CreateRouteController {
    private final CreateRouteService createRouteService;

    @PostMapping(value = CREATE_ROUTE_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateRouteRs> createRoute(@RequestBody @Valid CreateRouteRq rq) {
        return ResponseEntity.ok()
                .body(createRouteService.createRoute(rq));
    }

}
