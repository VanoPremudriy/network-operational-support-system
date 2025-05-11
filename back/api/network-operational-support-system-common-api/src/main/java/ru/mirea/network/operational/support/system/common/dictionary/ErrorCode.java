package ru.mirea.network.operational.support.system.common.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR_CODE("VALIDATION:ERROR"),
    NOT_FOUND_ERROR_CODE("NOT_FOUND:ERROR"),
    UNEXPECTED_ERROR_CODE("UNEXPECTED:ERROR"),

    EMPLOYEE_SEARCH_ERROR_CODE("EMPLOYEE_SEARCH:ERROR"),
    EMPLOYEE_AUTH_ERROR_CODE("EMPLOYEE_AUTH:ERROR"),

    TASK_ERROR_CODE("TASK:ERROR"),
    TASK_UNEXPECTED_ERROR_CODE("TASK:UNEXPECTED_ERROR"),

    ;

    private final String code;
}
