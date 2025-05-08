package ru.mirea.cnoss.model.authorization.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.mirea.cnoss.model.authorization.service.dto.SignInDto;
import ru.mirea.cnoss.model.authorization.service.dto.SignInJwtDto;
import ru.mirea.cnoss.model.authorization.service.dto.SignUpDto;
import ru.mirea.cnoss.model.authorization.service.dto.SignUpJwtDto;

@FeignClient(url = "http://localhost:8085", value = "authorization")
public interface AuthorizationService {

    @PostMapping(value = "/network-operational-support-system-api/auth")
    SignInJwtDto auth(@RequestBody SignInDto dto);

    @PostMapping(value = "/sign-up")
    SignUpJwtDto register(@RequestBody SignUpDto dto);
}