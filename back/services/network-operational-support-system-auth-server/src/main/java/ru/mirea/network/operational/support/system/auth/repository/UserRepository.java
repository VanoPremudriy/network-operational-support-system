package ru.mirea.network.operational.support.system.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.network.operational.support.system.auth.entity.Employees;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Employees, Long> {
    Optional<Employees> findByLogin(String login);
    boolean existsByLogin(String login);
}