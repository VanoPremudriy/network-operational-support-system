package ru.mirea.cnoss.service.client.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.client.dto.ClientViewDto;

@Getter
@Setter
public class ClientUpdateRequest {
    private String token;
    private ClientViewDto updatedClient;
}