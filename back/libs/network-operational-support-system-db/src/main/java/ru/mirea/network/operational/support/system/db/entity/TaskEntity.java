package ru.mirea.network.operational.support.system.db.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import ru.mirea.network.operational.support.system.db.dictionary.TaskType;

import java.time.LocalDateTime;
import java.util.Set;
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

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "resolved_date")
    private LocalDateTime resolvedDate;

    @Column(name = "execution_count", nullable = false)
    private Integer executionCount;

    @Column(name = "task_type", nullable = false)
    private String taskType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "task_data", nullable = false)
    private JsonNode taskData;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @BatchSize(size = 5)
    private Set<RouteEntity> routes;

    public TaskType getTaskType() {
        return TaskType.of(taskType);
    }
}
