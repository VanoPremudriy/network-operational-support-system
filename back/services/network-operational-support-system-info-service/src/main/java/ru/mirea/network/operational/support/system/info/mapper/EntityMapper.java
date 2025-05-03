package ru.mirea.network.operational.support.system.info.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.TaskEntity;
import ru.mirea.network.operational.support.system.info.api.info.node.DetailedNode;
import ru.mirea.network.operational.support.system.info.api.info.node.Node;
import ru.mirea.network.operational.support.system.info.api.info.node.NodeList;
import ru.mirea.network.operational.support.system.info.api.info.task.DetailedTask;
import ru.mirea.network.operational.support.system.info.api.info.task.Task;
import ru.mirea.network.operational.support.system.info.api.info.task.TaskList;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EntityMapper {
    Task map(TaskEntity taskEntity);

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
