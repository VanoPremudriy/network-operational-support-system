package ru.mirea.network.operational.support.system.back.exception;

import lombok.Getter;

@Getter
public class LockExceptionException extends RuntimeException {
    public LockExceptionException(String message) {
        super(message);
    }
}
