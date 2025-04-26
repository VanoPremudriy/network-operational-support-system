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

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "ports")
public class PortEntity {
    @Id
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(name = "board_id", unique = true, nullable = false)
    private String boardId;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(name = "link_id", unique = true, nullable = false)
    private String linkId;

    @Column(name = "port_type_id", unique = true, nullable = false)
    private String portTypeId;

}
