package ru.mirea.network.operational.support.system.auth.exception;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String username, String message) {
        super(username, message);
    }
}
