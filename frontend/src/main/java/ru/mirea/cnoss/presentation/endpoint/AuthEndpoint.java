package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.model.authorization.service.AuthorizationService;
import ru.mirea.cnoss.model.authorization.service.dto.ErrorDto;
import ru.mirea.cnoss.model.authorization.service.dto.SignInDto;
import ru.mirea.cnoss.model.authorization.service.dto.SignInJwtDto;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class AuthEndpoint {

    private final AuthorizationService authorizationService;

    //@TODO нормальная авторизация
    public SignInJwtDto auth() {

        SignInJwtDto dto = new SignInJwtDto();
        dto.setToken("token");
        dto.setSuccess(true);
        return dto;

//        SignInDto dto = new SignInDto();
//        dto.setLogin("admin");
//        dto.setPassword("admin");
//        SignInJwtDto auth = authorizationService.auth(dto);
//        System.out.println(auth.getToken());
//        return auth;
    }
}
