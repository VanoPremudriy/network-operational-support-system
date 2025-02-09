package ru.mirea.network.operational.support.system.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

@Component
public final class SavedUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler delegate;

    private final Consumer<OAuth2User> oauth2UserHandler;

    public SavedUserAuthenticationSuccessHandler(@Autowired Consumer<OAuth2User> oauth2UserHandler) {
        this.delegate = new SavedRequestAwareAuthenticationSuccessHandler();
        this.oauth2UserHandler = oauth2UserHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            this.oauth2UserHandler.accept((OAuth2User) authentication.getPrincipal());
        }

        this.delegate.onAuthenticationSuccess(request, response, authentication);
    }
}
