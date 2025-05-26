package ru.mirea.network.operational.support.system.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import org.springframework.data.domain.Persistable;

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
@Table(schema = "public", name = "boards")
public class BoardEntity implements Persistable<UUID> {
    @Id
    @Column(name = "id")
    private UUID id;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_model_id", nullable = false)
    private BoardModelEntity boardModel;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id", nullable = false)
    private BasketEntity basket;

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board")
    @BatchSize(size = 5)
    private Set<PortEntity> ports;

    private transient boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public Set<PortEntity> getPorts() {
        if (ports == null) {
            return new HashSet<>();
        }
        return ports;
    }
}
