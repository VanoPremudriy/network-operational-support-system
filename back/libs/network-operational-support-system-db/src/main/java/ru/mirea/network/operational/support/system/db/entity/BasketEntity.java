package ru.mirea.network.operational.support.system.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(schema = "public", name = "baskets")
public class BasketEntity implements Persistable<UUID> {
    @Id
    @Column(name = "id")
    private UUID id;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_model_id", nullable = false)
    private BasketModelEntity basketModel;

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "linked_basket_id")
    private BasketEntity linkedBasket;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "node_id", nullable = false)
    private NodeEntity node;

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "basket")
    @BatchSize(size = 5)
    private Set<BoardEntity> boards;

    private transient boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    public Set<BoardEntity> getBoards() {
        if (boards == null) {
            return new HashSet<>();
        }
        return boards;
    }
}
