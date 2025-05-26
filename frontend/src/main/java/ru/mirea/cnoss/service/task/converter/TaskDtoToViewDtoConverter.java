package ru.mirea.cnoss.service.task.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.task.dto.TaskData;
import ru.mirea.cnoss.service.task.dto.TaskDto;
import ru.mirea.cnoss.service.task.dto.TaskViewDto;

@Component
@RequiredArgsConstructor
public class TaskDtoToViewDtoConverter {

    private final ObjectMapper objectMapper;

    public TaskViewDto convert(TaskDto dto) {
        TaskViewDto viewDto = new TaskViewDto();

        viewDto.setId(dto.getId());
        viewDto.setActiveFlag(dto.isActiveFlag());
        viewDto.setCreatedTime(dto.getCreatedTime());
        viewDto.setResolvedDate(dto.getResolvedDate());
        viewDto.setExecutionCount(dto.getExecutionCount());
        viewDto.setTaskType(dto.getTaskType());
        viewDto.setStatus(dto.getStatus());
        viewDto.setClientName(dto.getClientName());

        try {
            TaskData taskData = objectMapper.readValue(dto.getTaskData(), TaskData.class);

            viewDto.setCapacity(taskData.getCapacity());
            viewDto.setClientId(taskData.getClientId());
            viewDto.setStartingPoint(taskData.getStartingPoint());
            viewDto.setDestinationPoint(taskData.getDestinationPoint());
        } catch (Exception e) {
            viewDto.setCapacity(null);
            viewDto.setClientId(null);
            viewDto.setStartingPoint(null);
            viewDto.setDestinationPoint(null);
            viewDto.setClientName(null);
        }

        return viewDto;
    }
}