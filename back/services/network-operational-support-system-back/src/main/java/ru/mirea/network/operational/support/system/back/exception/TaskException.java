package ru.mirea.network.operational.support.system.back.exception;

import lombok.Getter;

@Getter
public class TaskException extends RuntimeException {
    public TaskException(String message) {
        super(message);
    }
}
