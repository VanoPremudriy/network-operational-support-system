package ru.mirea.network.operational.support.system.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.auth.service.AuthenticationService;
import ru.mirea.network.operational.support.system.login.api.JwtAuthRs;
import ru.mirea.network.operational.support.system.login.api.SignInRq;
import ru.mirea.network.operational.support.system.login.api.SignUpRq;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("${controller.auth.sign-up}")
    public JwtAuthRs signUp(@RequestBody @Valid SignUpRq request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("${controller.auth.sign-in}")
    public JwtAuthRs signIn(@RequestBody @Valid SignInRq request) {
        return authenticationService.signIn(request);
    }


}

