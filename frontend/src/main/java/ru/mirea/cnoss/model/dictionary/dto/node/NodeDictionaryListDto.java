package ru.mirea.cnoss.model.dictionary.dto.node;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeDictionaryListDto {
    private List<NodeDictionaryDto> nodes;
}