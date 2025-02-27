package ru.mirea.network.operational.support.system.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.auth.entity.EmployeeEntity;
import ru.mirea.network.operational.support.system.login.api.JwtAuthRs;
import ru.mirea.network.operational.support.system.login.api.SignInRq;
import ru.mirea.network.operational.support.system.login.api.SignUpRq;

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
    public JwtAuthRs signUp(SignUpRq request) {
        EmployeeEntity user = EmployeeEntity.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .email(request.getEmail())
                .build();

        userService.create(user);

        return JwtAuthRs.builder().success(true).token(jwtService.generateToken(user)).build();
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthRs signIn(SignInRq request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        UserDetails user = userService.userDetailsService().loadUserByUsername(request.getLogin());

        return JwtAuthRs.builder().success(true).token(jwtService.generateToken(user)).build();
    }
}
