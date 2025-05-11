package ru.mirea.network.operational.support.system.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.auth.service.JwtService;
import ru.mirea.network.operational.support.system.login.api.JwtValidationRs;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/jwt/v1")
@RequiredArgsConstructor
@Tag(name = "Сервис работы с jwt")
public class JwtController {
    private final JwtService jwtService;
    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Operation(summary = "Валидация jwt")
    @GetMapping("${controller.jwt.validation}")
    public JwtValidationRs validation(@RequestHeader(HEADER_AUTHORIZATION) String token) {
        return jwtService.parseToken(jwtService.trimPrefix(token));
    }
}

