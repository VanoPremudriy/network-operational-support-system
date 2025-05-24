package ru.mirea.network.operational.support.system.info.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RouteRepository extends JpaRepository<RouteEntity, UUID> {
    void deleteByClientId(UUID clientId);
    Page<RouteEntity> findByTaskId(UUID taskId, Pageable pageable);
}