package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.network.operational.support.system.db.entity.BasketModelEntity;

import java.util.List;
import java.util.UUID;

public interface BasketModelRepository extends JpaRepository<BasketModelEntity, UUID> {
    @Query(value = """
            SELECT bm.* FROM basket_models bm
            inner join basket_allowed_board_lists bab on bab.basket_model_id = bm.id
            where bab.board_model_id = :id
            order by bm.price
            limit 1
            ;
            """,
            nativeQuery = true)
    BasketModelEntity findByBoardModelId(UUID id);


    List<BasketModelEntity> findByLevelNumberLessThanEqual(int levelNumber);
}