package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.model.dictionary.DictionaryService;
import ru.mirea.cnoss.model.dictionary.converter.CapacityDictionaryResponseToViewConverter;
import ru.mirea.cnoss.model.dictionary.dto.capacity.CapacityDictionaryViewResponse;
import ru.mirea.cnoss.model.dictionary.dto.client.ClientDictionaryResponse;
import ru.mirea.cnoss.model.dictionary.dto.node.NodeDictionaryResponse;


@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class DictionaryEndpoint {

    private final DictionaryService dictionaryService;
    private final CapacityDictionaryResponseToViewConverter converter;

    private final static String BEARER = "Bearer ";

    public ClientDictionaryResponse getClientDictionary(String token, String query) {
        token = BEARER + token;

        return dictionaryService.getClientDictionary(token, query);
    }

    public NodeDictionaryResponse getNodeDictionary(String token, String query) {
        token = BEARER + token;

        return dictionaryService.getNodeDictionary(token, query);
    }

    public CapacityDictionaryViewResponse getCapacityDictionary(String token, String query) {
        token = BEARER + token;

        return converter.convert(dictionaryService.getCapacityDictionary(token, query));
    }
}