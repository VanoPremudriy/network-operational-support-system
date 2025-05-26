package ru.mirea.network.operational.support.system.info.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.network.operational.support.system.common.api.BaseRs;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;
import ru.mirea.network.operational.support.system.db.entity.EmployeeToClientEntity;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTO;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTOWithId;
import ru.mirea.network.operational.support.system.info.api.client.GetAllClientsRq;
import ru.mirea.network.operational.support.system.info.api.client.GetAllClientsRs;
import ru.mirea.network.operational.support.system.info.mapper.EntityMapper;
import ru.mirea.network.operational.support.system.info.repository.ClientRepository;
import ru.mirea.network.operational.support.system.info.repository.EmployeeToClientRepository;
import ru.mirea.network.operational.support.system.info.repository.PortRepository;
import ru.mirea.network.operational.support.system.info.repository.RouteRepository;
import ru.mirea.network.operational.support.system.info.repository.TaskRepository;
import ru.mirea.network.operational.support.system.info.service.ClientService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RefreshScope
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @Value("${controller.client.get-all.pageSize:8}")
    private final Integer pageSize;

    private final ClientRepository clientRepository;
    private final PortRepository portRepository;
    private final RouteRepository routeRepository;
    private final TaskRepository taskRepository;
    private final EmployeeToClientRepository employeeToClientRepository;
    private final EntityMapper entityMapper;

    @Override
    @Transactional
    public BaseRs deleteClient(UUID id) {
        portRepository.deleteByClientId(id);
        routeRepository.deleteByClientId(id);
        routeRepository.flush();
        taskRepository.deleteByClientId(id);
        employeeToClientRepository.deleteByClientId(id);
        clientRepository.deleteById(id);

        return BaseRs.builder()
                .success(true)
                .build();
    }

    @Override
    @Transactional
    public BaseRs createClient(UUID employeeId, ClientDTO client) {
        ClientEntity clientEntity = entityMapper.map(client);

        clientEntity = clientRepository.save(clientEntity);

        EmployeeToClientEntity employeeToClientEntity = EmployeeToClientEntity.builder()
                .clientId(clientEntity.getId())
                .employeeId(employeeId)
                .build();

        employeeToClientRepository.save(employeeToClientEntity);

        return BaseRs.builder()
                .success(true)
                .build();
    }

    @Override
    public BaseRs updateClient(ClientDTOWithId client) {
        ClientEntity clientForUpdate = entityMapper.map(client);

        clientRepository.save(clientForUpdate);

        return BaseRs.builder()
                .success(true)
                .build();
    }

    @Override
    public GetAllClientsRs getAllClients(UUID employeeId, GetAllClientsRq rq) {
        PageRequest pageRequest = PageRequest.of(rq.getPageNumber(), pageSize);
        Page<ClientEntity> clients = clientRepository.findAllByEmployeeIdOrderByFirstName(employeeId, pageRequest);
        Set<ClientDTOWithId> clientsDTO = clients.get()
                .map(entityMapper::map)
                .collect(Collectors.toSet());

        return GetAllClientsRs.builder()
                .clients(clientsDTO)
                .numberOfPages(clients.getTotalPages())
                .success(true)
                .build();
    }
}
