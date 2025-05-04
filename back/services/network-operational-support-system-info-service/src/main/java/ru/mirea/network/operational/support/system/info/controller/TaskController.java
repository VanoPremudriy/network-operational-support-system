package ru.mirea.network.operational.support.system.info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.info.api.info.task.DetailedTaskRs;
import ru.mirea.network.operational.support.system.info.api.info.task.TaskListRs;
import ru.mirea.network.operational.support.system.info.service.TaskService;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping(value = "${controller.tasks-by-client}")
    public ResponseEntity<TaskListRs> getByClientId(@PathVariable("clientId") UUID clientId) {
        return ResponseEntity.ok()
                .body(taskService.getByClientId(clientId));
    }

    @GetMapping(value = "${controller.tasks-by-id}")
    public ResponseEntity<DetailedTaskRs> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok()
                .body(taskService.getById(id));
    }
}
