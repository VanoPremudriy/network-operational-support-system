package ru.mirea.cnoss.service.dictionary.dto.node;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NodeDictionaryDto {
    private UUID id;
    private String name;
}