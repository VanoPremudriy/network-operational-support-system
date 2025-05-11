package ru.mirea.network.operational.support.system.info.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.PortTypeEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTODetailed;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTOWithId;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTOWithLogin;
import ru.mirea.network.operational.support.system.info.api.dictionary.CapacityDTO;
import ru.mirea.network.operational.support.system.info.api.dictionary.ClientDTO;
import ru.mirea.network.operational.support.system.info.api.dictionary.NodeDTO;
import ru.mirea.network.operational.support.system.info.api.node.DetailedNode;
import ru.mirea.network.operational.support.system.info.api.node.Node;
import ru.mirea.network.operational.support.system.info.api.node.NodeList;
import ru.mirea.network.operational.support.system.info.api.task.DetailedTask;
import ru.mirea.network.operational.support.system.info.api.task.Task;
import ru.mirea.network.operational.support.system.info.api.task.TaskList;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EntityMapper {
    Task map(TaskEntity taskEntity);

    ClientEntity map(ClientDTOWithLogin clientDTO);

    ClientEntity map(ClientDTOWithId clientDTO);

    ClientDTODetailed map(ClientEntity clientEntity);

    NodeDTO mapDictionary(NodeEntity node);

    List<NodeDTO> mapNodeDictionary(List<NodeEntity> nodes);

    CapacityDTO mapDictionary(PortTypeEntity node);

    List<CapacityDTO> mapCapacityDictionary(List<PortTypeEntity> nodes);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    ClientDTO mapDictionary(ClientEntity node);

    List<ClientDTO> mapClientDictionary(List<ClientEntity> nodes);

    @Named("getFullName")
    default String getFullName(ClientEntity clientEntity) {
        return clientEntity.getLastName() + " " + clientEntity.getFirstName() + " " + clientEntity.getMiddleName();
    }

    DetailedTask mapDetailed(TaskEntity taskEntity);

    List<Task> mapTaskList(List<TaskEntity> tasks);

    Node map(NodeEntity node);

    DetailedNode mapDetailed(NodeEntity node);

    List<Node> mapNodeList(List<NodeEntity> nodes);

    default NodeList mapNode(List<NodeEntity> nodes) {
        if (nodes == null) {
            return NodeList.builder()
                    .build();
        } else {
            return NodeList.builder()
                    .nodes(mapNodeList(nodes))
                    .build();
        }
    }

    default TaskList mapTask(List<TaskEntity> tasks) {
        if (tasks == null) {
            return TaskList.builder()
                    .build();
        } else {
            return TaskList.builder()
                    .tasks(mapTaskList(tasks))
                    .build();
        }
    }

    default String map(JsonNode value) {
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }
}
