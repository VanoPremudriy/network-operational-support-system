package ru.mirea.network.operational.support.system.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.db.entity.EmployeeToClientEntity;

import java.util.UUID;

public interface EmployeeToClientRepository extends JpaRepository<EmployeeToClientEntity, UUID> {
    void deleteByClientId(UUID clientId);

}