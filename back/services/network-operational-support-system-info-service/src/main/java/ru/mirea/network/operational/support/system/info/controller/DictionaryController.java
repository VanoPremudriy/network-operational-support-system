package ru.mirea.network.operational.support.system.info.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.network.operational.support.system.common.dictionary.Constant;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindCapacityRs;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindClientRs;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindNodeRs;
import ru.mirea.network.operational.support.system.info.service.DictionaryService;

import java.util.UUID;

@Validated
@RefreshScope
@RestController
@RequestMapping("/v1/dictionary")
@RequiredArgsConstructor
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Operation(summary = "Поиск узла по его названию")
    @GetMapping(value = "${controller.dictionary.find-node}")
    public ResponseEntity<FindNodeRs> findNode(@RequestParam("query") @Nullable String query) {
        return ResponseEntity.ok()
                .body(dictionaryService.findNode(query));
    }

    @Operation(summary = "Поиск скорости по введенному значению")
    @GetMapping(value = "${controller.dictionary.find-capacity}")
    public ResponseEntity<FindCapacityRs> findCapacity(@RequestParam("query") @Nullable String query) {
        return ResponseEntity.ok()
                .body(dictionaryService.findCapacity(query));
    }

    @Operation(summary = "Поиск клиента по его ФИО")
    @GetMapping(value = "${controller.dictionary.find-client}")
    public ResponseEntity<FindClientRs> findClient(@RequestHeader(Constant.HEADER_ID) UUID employeeId,
                                                   @RequestParam("query") @Nullable String query) {
        return ResponseEntity.ok()
                .body(dictionaryService.findClient(employeeId, query));
    }
}
