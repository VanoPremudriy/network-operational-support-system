package ru.mirea.cnoss.service.client.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientGetRequest {
    private String token;
    private Integer currentPage;
}