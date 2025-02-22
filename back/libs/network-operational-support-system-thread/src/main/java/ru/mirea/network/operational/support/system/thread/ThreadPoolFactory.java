package ru.mirea.network.operational.support.system.thread;

import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public record ThreadPoolFactory(ThreadPoolTaskExecutorBuilder builder, TaskExecutionProperties properties) {
    public ThreadPoolTaskExecutor createFixExecutor(Integer corePoolsize, String threadNamePrefix) {
        if (corePoolsize == null || corePoolsize < 1) {
            corePoolsize = 1;
        }
        ThreadPoolTaskExecutorBuilder newBuilder = builder
                .corePoolSize(corePoolsize)
                .maxPoolSize(corePoolsize);

        return ThreadUtil.createExecutor(newBuilder, corePoolsize * 2, threadNamePrefix);
    }

    public ThreadPoolTaskExecutor createExecutor() {
        return ThreadUtil.createExecutor(builder,
                new PoolCustomizer()
                        .withCallerRunsPolicy()
                        .setQueueCapacity(ThreadUtil.checkCapacity(properties.getPool().getQueueCapacity()))
                        .customizer());
    }
}
