package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.presentation.utils.TokenUtils;
import ru.mirea.cnoss.service.authorization.AuthorizationService;
import ru.mirea.cnoss.service.authorization.dto.*;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class AuthEndpoint {

    private final AuthorizationService authorizationService;

    public SignInJwtDto auth(SignInDto dto) {
        SignInJwtDto result = authorizationService.auth(dto);

        if (result.isSuccess() && result.getToken() != null) {
            TokenUtils.setAuthCookie(result.getToken());
        } else {
            throw new RuntimeException("Auth failed");
        }

        return result;
    }

    public SignUpJwtDto register(SignUpDto dto) {
        return authorizationService.register(dto);
    }

    public boolean isAuthenticated() {
        return TokenUtils.extractTokenFromCookies(TokenUtils.getCurrentRequest()).isPresent();
    }

    public void logout() {
        TokenUtils.logout();
    }
}
