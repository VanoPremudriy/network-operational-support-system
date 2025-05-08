package ru.mirea.network.operational.support.system.common.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Headers {
    ID("Id"),
    EMAIL("Email"),
    MIDDLE_NAME("Middle-Name"),
    FIRST_NAME("First-Name"),
    LAST_NAME("Last-Name"),
    LOGIN("Login"),

    AUTHORIZATION("Authorization"),

    ;

    private final String name;
}
