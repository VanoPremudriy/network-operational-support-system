package ru.mirea.network.operational.support.system.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.auth.entity.Employees;
import ru.mirea.network.operational.support.system.auth.exception.UserAlreadyExistException;
import ru.mirea.network.operational.support.system.auth.exception.UserNotFoundException;
import ru.mirea.network.operational.support.system.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Employees save(Employees employees) {
        return repository.save(employees);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public UserDetails create(Employees employees) {
        if (repository.existsByLogin(employees.getLogin())) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }

        return save(employees);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public UserDetails getByUsername(String username) {
        return repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
