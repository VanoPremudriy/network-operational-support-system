package ru.mirea.cnoss.service.dictionary.dto.node;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeDictionaryListDto {
    private List<NodeDictionaryDto> nodes;
}