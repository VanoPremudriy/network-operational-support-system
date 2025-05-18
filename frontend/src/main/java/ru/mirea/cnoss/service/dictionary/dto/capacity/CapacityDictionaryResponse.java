package ru.mirea.cnoss.service.dictionary.dto.capacity;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

@Getter
@Setter
public class CapacityDictionaryResponse extends BaseResponse {
    private CapacityDictionaryListDto body;
}