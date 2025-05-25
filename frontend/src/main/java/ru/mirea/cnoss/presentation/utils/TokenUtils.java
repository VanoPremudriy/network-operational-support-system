package ru.mirea.cnoss.presentation.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

public class TokenUtils {

    public static final String TOKEN_COOKIE_NAME = "token";
    public static final String BEARER = "Bearer ";

    // Извлечение текущего запроса
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
    }

    // Достаём токен из куки
    public static Optional<String> extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();

        return Arrays.stream(request.getCookies())
                .filter(cookie -> TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    // Создаём HttpOnly cookie с токеном
    public static ResponseCookie createAuthCookie(String token) {
        return ResponseCookie.from(TOKEN_COOKIE_NAME, token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();
    }

    // Получить Bearer-токен (или бросить исключение)
    public static String getBearerTokenOrThrow() {
        return extractTokenFromCookies(getCurrentRequest())
                .map(t -> BEARER + t)
                .orElseThrow(() -> new RuntimeException("Token not found in cookies"));
    }

    public static void setAuthCookie(String token) {
        var cookie = createAuthCookie(token);
        var vaadinResponse = com.vaadin.flow.server.VaadinService.getCurrentResponse();
        if (vaadinResponse instanceof com.vaadin.flow.server.VaadinServletResponse servletResponse) {
            servletResponse.getHttpServletResponse()
                    .addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
    }

    public static void logout() {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0) // Удалить cookie
                .build();

        com.vaadin.flow.server.VaadinResponse vaadinResponse = com.vaadin.flow.server.VaadinService.getCurrentResponse();
        if (vaadinResponse instanceof com.vaadin.flow.server.VaadinServletResponse servletResponse) {
            servletResponse.getHttpServletResponse()
                    .addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
    }
}