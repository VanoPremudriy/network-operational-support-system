package ru.mirea.network.operational.support.system.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ErrorDTO {
    private Integer code;
    private String title;
    private String text;
}
