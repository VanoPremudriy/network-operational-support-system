package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    TaskEntity findByActiveFlagTrue();
}