package ru.mirea.network.operational.support.system.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class CallerRunsPolicyImpl extends ThreadPoolExecutor.CallerRunsPolicy {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.warn("Задача [{}] выполняется синхронно", r);

        super.rejectedExecution(r, executor);
    }
}
