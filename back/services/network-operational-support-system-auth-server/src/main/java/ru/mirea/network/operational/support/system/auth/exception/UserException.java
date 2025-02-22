package ru.mirea.network.operational.support.system.auth.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final String username;

    public UserException(String username, String message) {
        super(message);
        this.username = username;
    }
}
