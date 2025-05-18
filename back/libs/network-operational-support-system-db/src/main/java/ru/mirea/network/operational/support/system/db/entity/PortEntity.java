package ru.mirea.network.operational.support.system.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(schema = "public", name = "ports")
public class PortEntity implements Persistable<UUID> {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "port_type_id", nullable = false)
    private UUID portTypeId;

    @Column(name = "task_id", nullable = false)
    private UUID taskId;

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "link_to_the_associated_linear_port")
    private PortEntity linkToTheAssociatedLinearPort;

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "link_to_the_associated_linear_port_from_lower_level")
    private PortEntity linkToTheAssociatedLinearPortFromLowerLevel;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    private transient boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
