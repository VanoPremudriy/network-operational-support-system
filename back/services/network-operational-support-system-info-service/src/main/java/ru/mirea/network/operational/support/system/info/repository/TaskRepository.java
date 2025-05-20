package ru.mirea.network.operational.support.system.info.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findAllByClientId(UUID userId);

    @Query("select t from TaskEntity t " +
            "left join fetch t.routes " +
            "where t.id = :taskId")
    TaskEntity findByTaskIdWithRoutes(UUID taskId);

    void deleteByClientId(UUID clientId);

    @Query(value = "select * " +
            "from tasks " +
            "where client_id in (" +
            "    select client_id " +
            "    from employee_to_clients " +
            "    where employee_id = :employeeId) " +
            "order by created_time desc", nativeQuery = true)
    Page<TaskEntity> findAllByEmployeeIdOrderByCreatedTime(UUID employeeId, Pageable pageable);
}