package ru.mirea.cnoss.model.dictionary;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.cnoss.model.dictionary.dto.capacity.CapacityDictionaryResponse;
import ru.mirea.cnoss.model.dictionary.dto.client.ClientDictionaryResponse;
import ru.mirea.cnoss.model.dictionary.dto.node.NodeDictionaryResponse;

@FeignClient(url = "http://localhost:8085/network-operational-support-system-api/dictionaries", value = "dictionaries")
public interface DictionaryService {

    @GetMapping("/client")
    ClientDictionaryResponse getClientDictionary(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("query") String query);

    @GetMapping("/node")
    NodeDictionaryResponse getNodeDictionary(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("query") String query);

    @GetMapping("/capacity")
    CapacityDictionaryResponse getCapacityDictionary (
            @RequestHeader("Authorization") String authorization,
            @RequestParam("query") String query);

}