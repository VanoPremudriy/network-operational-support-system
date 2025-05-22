package ru.mirea.cnoss.service.task;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.mirea.cnoss.service.task.dto.GetAllTasksRequest;
import ru.mirea.cnoss.service.task.dto.GetAllTasksResponse;

@FeignClient(url = "http://localhost:8085/task", value = "tasks")
public interface TaskService {

    @PostMapping("/")
    GetAllTasksResponse getTasks(@RequestHeader("Authorization") String authorization,
                                 @RequestBody GetAllTasksRequest rq);
}