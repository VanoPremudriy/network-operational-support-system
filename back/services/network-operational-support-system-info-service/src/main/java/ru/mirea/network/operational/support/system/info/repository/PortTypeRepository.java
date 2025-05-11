package ru.mirea.network.operational.support.system.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.PortTypeEntity;
import ru.mirea.network.operational.support.system.db.entity.RouteEntity;

import java.util.List;
import java.util.UUID;

public interface PortTypeRepository extends JpaRepository<PortTypeEntity, UUID> {
    @Query("SELECT p FROM PortTypeEntity p " +
            "WHERE CAST(p.capacity AS string) " +
            "LIKE CONCAT('%', :q, '%')")
    List<PortTypeEntity> findAllByQuery(String q);
}