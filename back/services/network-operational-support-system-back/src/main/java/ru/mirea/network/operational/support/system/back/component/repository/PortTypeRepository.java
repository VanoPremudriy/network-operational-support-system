package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.db.entity.PortTypeEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface PortTypeRepository extends JpaRepository<PortTypeEntity, UUID> {
    PortTypeEntity findByCapacity(BigDecimal capacity);
}