package ru.mirea.network.operational.support.system.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(schema = "public", name = "nodes")
public class NodeEntity {
    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @EqualsAndHashCode.Exclude
    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @EqualsAndHashCode.Exclude
    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "name", nullable = false)
    private String name;

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "node")
    @BatchSize(size = 5)
    private Set<BasketEntity> baskets;

    @EqualsAndHashCode.Include
    private BigDecimal getLatitudeForEquals() {
        if (latitude == null){
            return null;
        }
        return latitude.stripTrailingZeros();
    }

    @EqualsAndHashCode.Include
    private BigDecimal getLongitudeForEquals() {
        if (longitude == null){
            return null;
        }
        return longitude.stripTrailingZeros();
    }

    public Set<BasketEntity> getBaskets() {
        if (baskets == null) {
            return new HashSet<>();
        }
        return baskets;
    }
}
