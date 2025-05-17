package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.BoardModelEntity;

import java.util.List;
import java.util.UUID;

public interface BoardModelRepository extends JpaRepository<BoardModelEntity, UUID> {
    @Query(value = """
            SELECT * FROM board_models bm
            inner join board_allowed_port_lists bap on bap.board_model_id = bm.id
            where bap.port_type_id = :id
            ;
            """,
            nativeQuery = true)
    BoardModelEntity findByPortTypeId(UUID id);


    List<BoardModelEntity> findByLevelNumberLessThan(int levelNumber);
}