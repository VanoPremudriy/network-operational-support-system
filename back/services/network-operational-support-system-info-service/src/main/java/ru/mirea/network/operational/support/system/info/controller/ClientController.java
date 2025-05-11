package ru.mirea.network.operational.support.system.info.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.common.api.BaseRs;
import ru.mirea.network.operational.support.system.common.dictionary.Constant;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTOWithId;
import ru.mirea.network.operational.support.system.info.api.client.ClientDTOWithLogin;
import ru.mirea.network.operational.support.system.info.api.client.GetAllClientsRq;
import ru.mirea.network.operational.support.system.info.api.client.GetAllClientsRs;
import ru.mirea.network.operational.support.system.info.service.ClientService;

import java.util.UUID;

@Validated
@RefreshScope
@RestController
@RequestMapping("/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "Удаление клиента")
    @DeleteMapping(value = "${controller.client.delete}")
    public ResponseEntity<BaseRs> deleteClient(@RequestParam("id") UUID id) {
        return ResponseEntity.ok()
                .body(clientService.deleteClient(id));
    }

    @Operation(summary = "Создание клиента")
    @PostMapping(value = "${controller.client.create}")
    public ResponseEntity<BaseRs> createClient(@RequestHeader(Constant.HEADER_ID) UUID employeeId,
                                               @RequestBody @Valid ClientDTOWithLogin client) {
        return ResponseEntity.ok()
                .body(clientService.createClient(employeeId, client));
    }

    @Operation(summary = "Обновление клиента")
    @PutMapping(value = "${controller.client.update}")
    public ResponseEntity<BaseRs> updateClient(@RequestBody @Valid ClientDTOWithId client) {
        return ResponseEntity.ok()
                .body(clientService.updateClient(client));
    }

    @Operation(summary = "Получение клиентов")
    @PostMapping(value = "${controller.client.get-all.endpoint}")
    public ResponseEntity<GetAllClientsRs> getAllClients(@RequestHeader(Constant.HEADER_ID) UUID employeeId,
                                                         @RequestBody @Valid GetAllClientsRq rq) {
        return ResponseEntity.ok()
                .body(clientService.getAllClients(employeeId, rq));
    }
}
