package ru.mirea.cnoss.service.task.converter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.task.dto.GetAllTasksViewResponse;
import ru.mirea.cnoss.service.task.dto.GetAllTasksResponse;
import ru.mirea.cnoss.service.task.dto.TaskViewDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskResponseToViewConverter {

    private final TaskDtoToViewDtoConverter converter;

    @SneakyThrows
    public GetAllTasksViewResponse convert(GetAllTasksResponse response) {
        GetAllTasksViewResponse viewResponse = new GetAllTasksViewResponse();

        viewResponse.setNumberOfPages(response.getNumberOfPages());
        viewResponse.setError(response.getError());
        viewResponse.setSuccess(response.isSuccess());

        Set<TaskViewDto> tasks = response.getTasks()
                .stream()
                .map(converter::convert)
                .collect(Collectors.toSet());

        viewResponse.setTasks(tasks);

        return viewResponse;
    }
}