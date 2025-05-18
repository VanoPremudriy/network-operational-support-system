package ru.mirea.cnoss.service.dictionary.dto.client;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDictionaryListDto {
    private List<ClientDictionaryDto> clients;
}