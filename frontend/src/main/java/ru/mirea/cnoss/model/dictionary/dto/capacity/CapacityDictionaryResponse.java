package ru.mirea.cnoss.model.dictionary.dto.capacity;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.model.BaseResponse;

@Getter
@Setter
public class CapacityDictionaryResponse extends BaseResponse {
    private CapacityDictionaryListDto body;
}