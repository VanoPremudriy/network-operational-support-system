package ru.mirea.network.operational.support.system.back.component.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.mirea.network.operational.support.system.thread.ThreadPoolFactory;

@Configuration
public class ThreadPoolConfig {
    private final ThreadPoolFactory factory;

    public ThreadPoolConfig(@Autowired ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder,
                            @Autowired TaskExecutionProperties taskExecutionProperties) {
        this.factory = new ThreadPoolFactory(threadPoolTaskExecutorBuilder, taskExecutionProperties);
    }

    @Bean(name = {AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME,
            TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME})
    public ThreadPoolTaskExecutor threadPoolFactory() {
        return factory.createExecutor();
    }

    @Bean
    public ThreadPoolTaskExecutor singleThreadExecutor() {
        return factory.createFixExecutor(1, "single-thread");
    }
}
