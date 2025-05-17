package ru.mirea.cnoss.model.dictionary.dto.client;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClientDictionaryDto {
    private UUID id;
    private String fullName;
}
