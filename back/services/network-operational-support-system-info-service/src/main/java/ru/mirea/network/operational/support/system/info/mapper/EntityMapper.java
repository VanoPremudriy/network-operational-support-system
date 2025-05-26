package ru.mirea.network.operational.support.system.info.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.network.operational.support.system.db.entity.BasketEntity;
import ru.mirea.network.operational.support.system.db.entity.BoardEntity;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.PortEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTO;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTOWithId;
import ru.mirea.network.operational.support.system.info.api.node.Basket;
import ru.mirea.network.operational.support.system.info.api.node.Board;
import ru.mirea.network.operational.support.system.info.api.node.DetailedNode;
import ru.mirea.network.operational.support.system.info.api.node.Node;
import ru.mirea.network.operational.support.system.info.api.node.NodeList;
import ru.mirea.network.operational.support.system.info.api.node.Port;
import ru.mirea.network.operational.support.system.info.api.task.DetailedTask;
import ru.mirea.network.operational.support.system.info.api.task.Task;
import ru.mirea.network.operational.support.system.info.api.task.TaskList;
import ru.mirea.network.operational.support.system.info.repository.ClientRepository;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = ClientRepository.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class EntityMapper {
    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Mapping(target = "clientName", source = "client", qualifiedByName = "getFullName")
    public abstract Task map(TaskEntity taskEntity);

    @Named("getFullNameById")
    String getFullNameById(UUID clientId) {
        return getFullName(clientRepository.findById(clientId).orElseThrow());
    }

    @Named("getFullName")
    String getFullName(ClientEntity clientEntity) {
        return clientEntity.getLastName() + " " + clientEntity.getFirstName() + " " + clientEntity.getMiddleName();
    }

    public abstract ClientEntity map(ClientDTOWithId clientDTO);

    public abstract ClientEntity map(ClientDTO clientDTO);

    public abstract ClientDTOWithId map(ClientEntity clientEntity);

    @Mapping(target = "routes", ignore = true)
    public abstract DetailedTask mapDetailed(TaskEntity taskEntity);

    public abstract List<Task> mapTaskList(List<TaskEntity> tasks);

    public abstract Node map(NodeEntity node);

    public abstract DetailedNode mapDetailed(NodeEntity node);

    @Mapping(target = "name", source = "basketModel.name")
    public abstract Basket mapDetailed(BasketEntity basket);

    @Mapping(target = "name", source = "boardModel.name")
    public abstract Board mapDetailed(BoardEntity board);

    @Mapping(target = "clientName", source = "clientId", qualifiedByName = "getFullNameById")
    @Mapping(target = "portTypeName", source = "portType.name")
    public abstract Port mapDetailed(PortEntity port);

    public abstract List<Node> mapNodeList(List<NodeEntity> nodes);

    public NodeList mapNode(List<NodeEntity> nodes) {
        if (nodes == null) {
            return NodeList.builder()
                    .build();
        } else {
            return NodeList.builder()
                    .nodes(mapNodeList(nodes))
                    .build();
        }
    }

    UUID map(PortEntity value) {
        return value.getId();
    }

    public TaskList mapTask(List<TaskEntity> tasks) {
        if (tasks == null) {
            return TaskList.builder()
                    .build();
        } else {
            return TaskList.builder()
                    .tasks(mapTaskList(tasks))
                    .build();
        }
    }

    String map(JsonNode value) {
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }
}
