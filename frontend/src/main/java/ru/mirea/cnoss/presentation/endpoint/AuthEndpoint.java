package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.service.authorization.AuthorizationService;
import ru.mirea.cnoss.service.authorization.dto.*;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class AuthEndpoint {

    private final AuthorizationService authorizationService;

    public SignInJwtDto auth(SignInDto dto) {
        return authorizationService.auth(dto);
    }

    public SignUpJwtDto register(SignUpDto dto) {
        return authorizationService.register(dto);
    }
}
