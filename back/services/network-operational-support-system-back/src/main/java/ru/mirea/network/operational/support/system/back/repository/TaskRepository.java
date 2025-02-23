package ru.mirea.network.operational.support.system.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.network.operational.support.system.back.entity.TaskEntity;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

}