package ru.mirea.network.operational.support.system.back.component.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;


import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID>, JpaSpecificationExecutor<ClientEntity> {
}