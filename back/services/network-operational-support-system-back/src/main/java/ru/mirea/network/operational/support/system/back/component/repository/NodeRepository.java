package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;

import java.util.UUID;

public interface NodeRepository extends JpaRepository<NodeEntity, UUID> {
    @Query("""
            select n from NodeEntity n 
            left join fetch n.baskets ba 
            left join fetch ba.basketModel 
            left join fetch ba.boards bo
            left join fetch ba.linkedBasket lba
            left join fetch lba.basketModel
            left join fetch lba.boards
            left join fetch lba.node
            left join fetch bo.boardModel
            left join fetch bo.ports p
            left join fetch p.portType
            left join fetch p.linkToTheAssociatedLinearPort
            left join fetch p.linkToTheAssociatedLinearPortFromLowerLevel
            where n.id = :nodeId
            """)
    NodeEntity findByNodeIdDetailed(UUID nodeId);
}