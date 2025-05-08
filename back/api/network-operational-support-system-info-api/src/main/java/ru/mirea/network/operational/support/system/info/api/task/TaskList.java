package ru.mirea.network.operational.support.system.info.api.task;


import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class TaskList {
    private List<Task> tasks;
}
