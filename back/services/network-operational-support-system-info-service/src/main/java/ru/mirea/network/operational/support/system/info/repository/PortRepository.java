package ru.mirea.network.operational.support.system.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.db.entity.PortEntity;

import java.util.UUID;

public interface PortRepository extends JpaRepository<PortEntity, UUID> {
    void deleteByClientId(UUID clientId);
}