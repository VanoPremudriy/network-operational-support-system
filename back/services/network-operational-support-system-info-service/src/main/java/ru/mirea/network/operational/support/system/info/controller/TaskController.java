package ru.mirea.network.operational.support.system.info.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.common.dictionary.Constant;
import ru.mirea.network.operational.support.system.info.api.task.DetailedTaskRs;
import ru.mirea.network.operational.support.system.info.api.task.GetAllTasksRq;
import ru.mirea.network.operational.support.system.info.api.task.GetAllTasksRs;
import ru.mirea.network.operational.support.system.info.api.task.TaskListRs;
import ru.mirea.network.operational.support.system.info.service.TaskService;

import java.util.UUID;

@Validated
@RefreshScope
@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping(value = "${controller.task.get-by-client}")
    public ResponseEntity<TaskListRs> getByClientId(@PathVariable("clientId") UUID clientId) {
        return ResponseEntity.ok()
                .body(taskService.getByClientId(clientId));
    }

    @GetMapping(value = "${controller.task.get-by-id}")
    public ResponseEntity<DetailedTaskRs> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok()
                .body(taskService.getById(id));
    }

    @PostMapping(value = "${controller.task.get-all.endpoint}")
    public ResponseEntity<GetAllTasksRs> getAllTasks(@RequestHeader(Constant.HEADER_ID) UUID employeeId,
                                                       @RequestBody @Valid GetAllTasksRq rq) {
        return ResponseEntity.ok()
                .body(taskService.getAllTasks(employeeId, rq));
    }
}
