package ru.mirea.network.operational.support.system.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.auth.entity.EmployeeEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByLogin(String login);

    boolean existsByLogin(String login);
}