package ru.mirea.network.operational.support.system.db.entity;

import com.fasterxml.jackson.databind.JsonNode;
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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
@Table(schema = "public", name = "routes")
public class RouteEntity {
    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "route_data", nullable = false)
    private JsonNode routeData;

    @Column(name = "task_id", nullable = false)
    private UUID taskId;

    @Column(name = "price")
    private BigDecimal price;
}
