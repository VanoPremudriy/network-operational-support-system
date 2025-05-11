package ru.mirea.network.operational.support.system.gateway.exception;

import lombok.Getter;
import ru.mirea.network.operational.support.system.common.api.BaseRs;

@Getter
public class ResponseValidationException extends RuntimeException {
    private final BaseRs response;

    public ResponseValidationException(BaseRs response) {
        super(response.getError() == null
                ? "Проверьте правильность введенных данных"
                : response.getError().getTitle());
        this.response = response;
    }
}
