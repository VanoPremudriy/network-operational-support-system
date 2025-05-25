package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.presentation.utils.TokenUtils;
import ru.mirea.cnoss.service.task.TaskService;
import ru.mirea.cnoss.service.task.converter.TaskResponseToViewConverter;
import ru.mirea.cnoss.service.task.dto.GetAllTasksRequest;
import ru.mirea.cnoss.service.task.dto.GetAllTasksResponse;
import ru.mirea.cnoss.service.task.dto.GetAllTasksViewResponse;
import ru.mirea.cnoss.service.task.dto.TaskGetRequest;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class TaskEndpoint {

    private final TaskService taskService;
    private final TaskResponseToViewConverter converter;

    public GetAllTasksViewResponse getAllTasks(TaskGetRequest request) {
        String token = TokenUtils.getBearerTokenOrThrow();

        GetAllTasksRequest rq = GetAllTasksRequest.builder()
                .pageNumber(request.getCurrentPage())
                .build();

        GetAllTasksResponse response = taskService.getTasks(token, rq);

        return converter.convert(response);
    }
}