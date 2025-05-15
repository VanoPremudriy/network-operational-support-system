package ru.mirea.cnoss.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.model.authorization.service.dto.ErrorDto;

@Getter
@Setter
public class BaseResponse {
    @JsonProperty("success")
    private boolean success;

    @Nullable
    @JsonProperty("error")
    private ErrorDto error;
}