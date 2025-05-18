package ru.mirea.cnoss.service.dictionary.dto.client;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

@Getter
@Setter
public class ClientDictionaryResponse extends BaseResponse {
    private ClientDictionaryListDto body;
}