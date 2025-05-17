package ru.mirea.cnoss.model.dictionary.dto.node;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.model.BaseResponse;

@Getter
@Setter
public class NodeDictionaryResponse extends BaseResponse {
    private NodeDictionaryListDto body;
}