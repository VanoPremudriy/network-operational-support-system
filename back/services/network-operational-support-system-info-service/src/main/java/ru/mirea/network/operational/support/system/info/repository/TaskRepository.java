package ru.mirea.network.operational.support.system.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findAllByClientId(UUID userId);

    @Query("select t from TaskEntity t " +
            "left join fetch t.routes " +
            "where t.id = :taskId")
    TaskEntity findByTaskIdWithRoutes(UUID taskId);
}