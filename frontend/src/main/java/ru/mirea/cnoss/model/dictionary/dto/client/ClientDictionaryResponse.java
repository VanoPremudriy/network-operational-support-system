package ru.mirea.cnoss.model.dictionary.dto.client;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.model.BaseResponse;

@Getter
@Setter
public class ClientDictionaryResponse extends BaseResponse {
    private ClientDictionaryListDto body;
}