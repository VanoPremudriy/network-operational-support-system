package ru.mirea.network.operational.support.system.auth.exception;

public class UserAlreadyExistException extends UserException {
    public UserAlreadyExistException(String username, String message) {
        super(username, message);
    }
}
