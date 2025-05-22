package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskStatus;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    TaskEntity findByActiveFlagTrue();

    @Query("select t from TaskEntity t " +
            "left join fetch t.routes " +
            "where t.id = :taskId")
    TaskEntity findByIdWithRoute(UUID taskId);
}