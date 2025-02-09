package ru.mirea.network.operational.support.system.auth.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @author: ReLive
 * @date: 2022/8/02 20:02 下午
 */
@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_mtm_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private List<RoleEntity> roleEntityList;
}
