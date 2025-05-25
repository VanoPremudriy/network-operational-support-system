package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.presentation.utils.TokenUtils;
import ru.mirea.cnoss.service.dictionary.DictionaryService;
import ru.mirea.cnoss.service.dictionary.converter.CapacityDictionaryResponseToViewConverter;
import ru.mirea.cnoss.service.dictionary.dto.capacity.CapacityDictionaryViewResponse;
import ru.mirea.cnoss.service.dictionary.dto.client.ClientDictionaryResponse;
import ru.mirea.cnoss.service.dictionary.dto.node.NodeDictionaryResponse;


@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class DictionaryEndpoint {

    private final DictionaryService dictionaryService;
    private final CapacityDictionaryResponseToViewConverter converter;

    private final static String BEARER = "Bearer ";

    public ClientDictionaryResponse getClientDictionary(String query) {
        String token = TokenUtils.getBearerTokenOrThrow();

        return dictionaryService.getClientDictionary(token, query);
    }

    public NodeDictionaryResponse getNodeDictionary(String query) {
        String token = TokenUtils.getBearerTokenOrThrow();

        return dictionaryService.getNodeDictionary(token, query);
    }

    public CapacityDictionaryViewResponse getCapacityDictionary(String query) {
        String token = TokenUtils.getBearerTokenOrThrow();

        return converter.convert(dictionaryService.getCapacityDictionary(token, query));
    }
}