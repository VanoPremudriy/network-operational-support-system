package ru.mirea.cnoss.service.dictionary.dto.node;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

@Getter
@Setter
public class NodeDictionaryResponse extends BaseResponse {
    private NodeDictionaryListDto body;
}