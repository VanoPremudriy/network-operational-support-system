package ru.mirea.cnoss.model.authorization.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.mirea.cnoss.model.authorization.service.dto.SignInDto;
import ru.mirea.cnoss.model.authorization.service.dto.SignInJwtDto;

@FeignClient(url = "http://localhost:8085/network-operational-support-system-api", value = "authorization")
public interface AuthorizationService {

    @PostMapping(value = "/auth")
    SignInJwtDto auth(@RequestBody SignInDto dto);
}