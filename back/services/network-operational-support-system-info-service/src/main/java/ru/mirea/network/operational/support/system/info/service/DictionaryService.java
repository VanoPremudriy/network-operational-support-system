package ru.mirea.network.operational.support.system.info.service;

import ru.mirea.network.operational.support.system.info.api.dictionary.FindCapacityRs;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindClientRs;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindNodeRs;

import java.util.UUID;

public interface DictionaryService {
    FindNodeRs findNode(String query);

    FindCapacityRs findCapacity(String query);

    FindClientRs findClient(UUID employeeId, String query);
}
