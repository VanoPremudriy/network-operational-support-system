package ru.mirea.cnoss.model.client.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetAllClientsRequest {
    private Integer pageNumber;
}