package ru.mirea.network.operational.support.system.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(schema = "public", name = "port_types")
public class PortTypeEntity {
    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @EqualsAndHashCode.Exclude
    @Column(name = "capacity", nullable = false)
    private BigDecimal capacity;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "num_unit", nullable = false)
    private String numUnit;

    @EqualsAndHashCode.Exclude
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @EqualsAndHashCode.Include
    private BigDecimal getPriceForEquals() {
        return price.stripTrailingZeros();
    }

    @EqualsAndHashCode.Include
    private BigDecimal getCapacityForEquals() {
        return capacity.stripTrailingZeros();
    }
}
