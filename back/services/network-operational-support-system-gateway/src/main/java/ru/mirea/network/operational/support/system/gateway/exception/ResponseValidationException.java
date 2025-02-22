package ru.mirea.network.operational.support.system.gateway.exception;

import lombok.Getter;
import ru.mirea.network.operational.support.system.api.dto.BaseRs;

@Getter
public class ResponseValidationException extends RuntimeException {
    private final BaseRs response;

    public ResponseValidationException(BaseRs response) {
        super(response.getErrorDTO().getTitle());
        this.response = response;
    }
}
