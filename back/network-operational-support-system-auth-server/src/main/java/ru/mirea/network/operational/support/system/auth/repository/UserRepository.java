package ru.mirea.network.operational.support.system.auth.repository;

import ru.mirea.network.operational.support.system.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: ReLive
 * @date: 2022/8/02 20:20 下午
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserByUsername(String username);
}
