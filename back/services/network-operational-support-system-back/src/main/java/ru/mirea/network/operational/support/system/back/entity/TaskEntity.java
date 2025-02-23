package ru.mirea.network.operational.support.system.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "tasks")
public class TaskEntity {
    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(name = "active_flag", unique = true, nullable = false)
    private Boolean activeFlag;

    @Column(name = "client_id", unique = true, nullable = false)
    private UUID clientId;

    @Column(name = "created_time", unique = true, nullable = false)
    private LocalDateTime createdTime;

}
