package ru.mirea.network.operational.support.system.auth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.auth.dictionary.Constant;
import ru.mirea.network.operational.support.system.auth.entity.EmployeeEntity;
import ru.mirea.network.operational.support.system.login.api.JwtValidationRs;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RefreshScope
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    public String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String trimPrefix(String token) {
        return token.substring(Constant.BEARER_PREFIX.length());
    }

    /**
     * Генерация токена
     *
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof EmployeeEntity customEmployeeDetails) {
            claims.put("id", customEmployeeDetails.getId());
            if (customEmployeeDetails.getEmail() != null){
                claims.put("email", customEmployeeDetails.getEmail());
            }
            claims.put("login", customEmployeeDetails.getLogin());
            claims.put("first_name", customEmployeeDetails.getFirstName());
            claims.put("last_name", customEmployeeDetails.getLastName());
            if (customEmployeeDetails.getMiddleName() != null) {
                claims.put("middle_name", customEmployeeDetails.getMiddleName());
            }
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String login = extractLogin(token);
        return (login.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Получение данных из токена
     *
     * @param token токен
     * @return JwtValidationResponse, данные токена
     */
    public JwtValidationRs parseToken(String token) {
        final Claims claims = extractAllClaims(token);

        return JwtValidationRs.builder()
                .id(claims.get("id", String.class))
                .email(claims.get("email", String.class))
                .firstName(claims.get("first_name", String.class))
                .lastName(claims.get("last_name", String.class))
                .middleName(claims.get("middle_name", String.class))
                .login(claims.get("login", String.class))
                .success(true)
                .build();
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токена
     *
     * @param extraClaims дополнительные данные
     * @param userDetails данные пользователя
     * @return токен
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получение ключа для подписи токена
     *
     * @return ключ
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
