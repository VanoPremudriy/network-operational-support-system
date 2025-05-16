package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NodeRepository extends JpaRepository<NodeEntity, UUID> {
    @Query("select n from NodeEntity n " +
            "left join fetch n.baskets ba " +
            "left join fetch ba.boards bo " +
            "left join fetch bo.ports " +
            "where n.id = :nodeId")
    NodeEntity findByNodeIdDetailed(UUID nodeId);
}