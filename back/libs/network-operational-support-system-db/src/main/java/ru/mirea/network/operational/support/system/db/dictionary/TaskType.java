package ru.mirea.network.operational.support.system.db.dictionary;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum TaskType {
    CALCULATE_ROUTE;

    public static TaskType of(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }
}
