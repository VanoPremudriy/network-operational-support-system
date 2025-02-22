package ru.mirea.network.operational.support.system.thread;

import lombok.experimental.UtilityClass;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.boot.task.ThreadPoolTaskExecutorCustomizer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@UtilityClass
public class ThreadUtil {
    public static final int MIN_POOL_QUEUE_CAPACITY = 10;
    public static final int AVG_POOL_QUEUE_CAPACITY = 50;
    public static final int MAX_POOL_QUEUE_CAPACITY = 10;

    public static ThreadPoolTaskExecutor createExecutor(ThreadPoolTaskExecutorBuilder builder,
                                                        Integer capacity,
                                                        String threadNamePrefix) {
        return createExecutor(builder, createCustomizer(capacity, threadNamePrefix));
    }

    public static ThreadPoolTaskExecutor createExecutor(ThreadPoolTaskExecutorBuilder builder,
                                                        ThreadPoolTaskExecutorCustomizer customizer) {
        ThreadPoolTaskExecutor executor = builder.build();
        if (customizer != null) {
            customizer.customize(executor);
        }
        executor.initialize();
        return executor;
    }

    public static int checkCapacity(Integer capacity) {
        if (capacity == null) {
            return AVG_POOL_QUEUE_CAPACITY;
        }
        if (capacity < MIN_POOL_QUEUE_CAPACITY) {
            return MIN_POOL_QUEUE_CAPACITY;
        } else if (capacity > MAX_POOL_QUEUE_CAPACITY) {
            return MAX_POOL_QUEUE_CAPACITY;
        }
        return capacity;
    }

    public static ThreadPoolTaskExecutorCustomizer createCustomizer(Integer capacity, String threadNamePrefix) {
        return new PoolCustomizer()
                .withCallerRunsPolicy()
                .setQueueCapacity(checkCapacity(capacity))
                .setThreadNamePrefix(threadNamePrefix)
                .customizer();
    }
}
