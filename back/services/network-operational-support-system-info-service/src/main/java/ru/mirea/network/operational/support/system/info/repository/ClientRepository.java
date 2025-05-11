package ru.mirea.network.operational.support.system.info.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID>, JpaSpecificationExecutor<ClientEntity> {

    @Query(value = "SELECT c FROM EmployeeEntity e JOIN e.clients c WHERE e.id = :employeeId ORDER BY c.firstName",
            countQuery = "SELECT COUNT(c) FROM ClientEntity c")
    Page<ClientEntity> findAllByEmployeeIdOrderByFirstName(UUID employeeId, Pageable pageable);

    @Query(value = "SELECT c FROM EmployeeEntity e JOIN e.clients c WHERE e.id = :employeeId")
    List<ClientEntity> findAllByEmployeeId(UUID employeeId);
}