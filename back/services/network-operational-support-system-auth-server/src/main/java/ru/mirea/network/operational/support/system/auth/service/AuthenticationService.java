package ru.mirea.network.operational.support.system.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.api.login.JwtAuthenticationResponse;
import ru.mirea.network.operational.support.system.api.login.SignInRequest;
import ru.mirea.network.operational.support.system.api.login.SignUpRequest;
import ru.mirea.network.operational.support.system.auth.entity.Employees;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        Employees user = Employees.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .email(request.getEmail())
                .build();

        userService.create(user);

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        UserDetails user = userService.userDetailsService().loadUserByUsername(request.getLogin());

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }
}
