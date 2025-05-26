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
@Table(schema = "public", name = "board_models")
public class BoardModelEntity {
    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "level_number", nullable = false)
    private Integer levelNumber;

    @Column(name = "number_of_slots", nullable = false)
    private Integer numberOfSlots;

    @Column(name = "can_send_to_different_level")
    private Boolean canSendToDifferentLevel;

    @Column(name = "is_linear", nullable = false)
    private boolean isLinear;

    @EqualsAndHashCode.Exclude
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @EqualsAndHashCode.Include
    private BigDecimal getPriceForEquals() {
        if (price == null){
            return null;
        }
        return price.stripTrailingZeros();
    }

    public BigDecimal getPrice() {
        if (price == null) {
            return BigDecimal.ZERO;
        }
        return price;
    }
}
