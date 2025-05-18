package ru.mirea.cnoss.service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.mirea.cnoss.service.BaseResponse;
import ru.mirea.cnoss.service.client.dto.ClientCreateDto;
import ru.mirea.cnoss.service.client.dto.ClientResponse;
import ru.mirea.cnoss.service.client.dto.ClientUpdateDto;
import ru.mirea.cnoss.service.client.dto.request.GetAllClientsRequest;

import java.util.UUID;

@FeignClient(url = "http://localhost:8085/network-operational-support-system-api", value = "clients")
public interface ClientService {

    @PostMapping(value = "/client")
    ClientResponse getClients(@RequestHeader("Authorization") String authorization,
                              @RequestBody GetAllClientsRequest rq);

    @PostMapping("/client/create")
    BaseResponse createClient(@RequestHeader("Authorization") String authorization,
                              @RequestBody ClientCreateDto client);

    @DeleteMapping("/client/delete")
    BaseResponse deleteClient(@RequestHeader("Authorization") String authorization,
                              @RequestParam UUID id);

    @PutMapping("/client/update")
    BaseResponse updateClient(@RequestHeader("Authorization") String authorization,
                              @RequestBody ClientUpdateDto client);

}