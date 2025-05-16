package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.db.entity.PortEntity;

import java.util.UUID;

public interface PortRepository extends JpaRepository<PortEntity, UUID> {
}