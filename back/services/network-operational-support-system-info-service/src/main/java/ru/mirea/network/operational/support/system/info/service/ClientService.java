package ru.mirea.network.operational.support.system.info.service;

import ru.mirea.network.operational.support.system.common.api.BaseRs;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTO;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTOWithId;
import ru.mirea.network.operational.support.system.info.api.client.GetAllClientsRq;
import ru.mirea.network.operational.support.system.info.api.client.GetAllClientsRs;

import java.util.UUID;

public interface ClientService {
    BaseRs deleteClient(UUID id);

    BaseRs createClient(UUID employeeId, ClientDTO client);

    BaseRs updateClient(ClientDTOWithId client);

    GetAllClientsRs getAllClients(UUID employeeId, GetAllClientsRq rq);
}
