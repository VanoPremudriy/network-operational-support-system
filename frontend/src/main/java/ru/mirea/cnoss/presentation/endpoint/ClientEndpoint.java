package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.service.BaseResponse;
import ru.mirea.cnoss.service.client.ClientService;
import ru.mirea.cnoss.service.client.converter.ClientDtoToViewDtoConverter;
import ru.mirea.cnoss.service.client.dto.*;
import ru.mirea.cnoss.service.client.dto.request.ClientGetRequest;
import ru.mirea.cnoss.service.client.dto.request.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class ClientEndpoint {

    private final ClientService clientService;
    private final ClientDtoToViewDtoConverter converter;

    private static final String BEARER = "Bearer ";

    public ClientViewResponse getClients(ClientGetRequest request) {
        String userToken =  BEARER + request.getToken();
        Integer currentPage = request.getCurrentPage();

        GetAllClientsRequest rq = GetAllClientsRequest.builder()
                .pageNumber(currentPage)
                .build();

        ClientResponse response = clientService.getClients(userToken, rq);

        Set<ClientViewDto> clients = response.getClients().stream().map(converter::convert).collect(Collectors.toSet());

        ClientViewResponse viewResponse = new ClientViewResponse();
        viewResponse.setSuccess(response.isSuccess());
        viewResponse.setClients(clients);
        viewResponse.setNumberOfPages(response.getNumberOfPages());
        viewResponse.setError(response.getError());

        return viewResponse;
    }

    public BaseResponse createClient(ClientCreateRequest request) {
        String userToken = BEARER + request.getToken();
        ClientViewDto newClient = request.getNewClient();
        ClientCreateDto newCreateClient = ClientCreateDto.builder()
                .email(newClient.getEmail())
                .login(newClient.getLogin())
                .lastName(newClient.getLastName())
                .middleName(newClient.getMiddleName())
                .firstName(newClient.getFirstName())
                .build();
        return clientService.createClient(userToken, newCreateClient);
    }

    public BaseResponse updateClient(ClientUpdateRequest request) {
        String userToken = BEARER + request.getToken();
        ClientViewDto updatedClient = request.getUpdatedClient();
        ClientUpdateDto updateClient = ClientUpdateDto.builder()
                .id(updatedClient.getId())
                .email(updatedClient.getEmail())
                .lastName(updatedClient.getLastName())
                .middleName(updatedClient.getMiddleName())
                .firstName(updatedClient.getFirstName())
                .build();
        return clientService.updateClient(userToken, updateClient);
    }

    public BaseResponse deleteClient(ClientDeleteRequest request) {
        String userToken = BEARER + request.getToken();
        UUID clientId = UUID.fromString(request.getClientId());
        return clientService.deleteClient(userToken, clientId);
    }
}