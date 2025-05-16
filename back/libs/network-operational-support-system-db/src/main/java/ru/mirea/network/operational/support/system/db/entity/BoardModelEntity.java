package ru.mirea.network.operational.support.system.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private String numberOfSlots;

    @Column(name = "can_send_to_a_lower_level")
    private Boolean canSendToALowerLevel;

    @Column(name = "is_linear", nullable = false)
    private boolean isLinear;

    @Column(name = "price")
    private BigDecimal price;
}
