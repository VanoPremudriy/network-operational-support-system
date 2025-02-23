package ru.mirea.network.operational.support.system.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class UncaughtExceptionHandlerImpl implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Метод [{}] с параметрами [{}] прерван ошибкой [{}] \n StackTrace:", method, params, ex.getMessage(), ex);
    }
}
