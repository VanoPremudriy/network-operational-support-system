package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;

import java.util.UUID;

public interface RouteRepository extends JpaRepository<RouteEntity, UUID> {

}