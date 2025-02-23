package ru.mirea.network.operational.support.system.thread;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.task.ThreadPoolTaskExecutorCustomizer;

import java.util.concurrent.RejectedExecutionHandler;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class PoolCustomizer {
    private RejectedExecutionHandler rejectedExecutionHandler;
    private Integer queueCapacity;
    private String threadNamePrefix;

    public ThreadPoolTaskExecutorCustomizer customizer() {
        return executor -> {
            if (rejectedExecutionHandler != null) {
                executor.setRejectedExecutionHandler(rejectedExecutionHandler);
            }
            if (queueCapacity != null && queueCapacity > 0) {
                executor.setQueueCapacity(queueCapacity);
            }
            if (StringUtils.isNotBlank(threadNamePrefix)) {
                executor.setThreadNamePrefix(threadNamePrefix);
            }
        };
    }

    public PoolCustomizer withCallerRunsPolicy() {
        return this.setRejectedExecutionHandler(new CallerRunsPolicyImpl());
    }
}
