package ru.mirea.cnoss.service.authorization.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Builder
@Jacksonized
public class ErrorDto {
    private String code;
    private String title;
    private Map<String, String> infos;
}
