package ru.mirea.cnoss.model.client.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDeleteRequest {
    private String token;
    private String clientId;
}