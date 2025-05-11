package ru.mirea.network.operational.support.system.common.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Headers {
    ID(Constant.HEADER_ID),
    EMAIL("Email"),
    MIDDLE_NAME("Middle-Name"),
    FIRST_NAME("First-Name"),
    LAST_NAME("Last-Name"),
    LOGIN("Login"),

    AUTHORIZATION(Constant.HEADER_AUTHORIZATION),

    ;

    private final String name;
}
