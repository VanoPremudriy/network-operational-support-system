package ru.mirea.network.operational.support.system.info.service.impl;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.common.dictionary.ErrorCode;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;
import ru.mirea.network.operational.support.system.db.entity.EmployeeToClientEntity;
import ru.mirea.network.operational.support.system.info.api.dictionary.CapacityDTOList;
import ru.mirea.network.operational.support.system.info.api.dictionary.ClientDTOList;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindCapacityRs;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindClientRs;
import ru.mirea.network.operational.support.system.info.api.dictionary.FindNodeRs;
import ru.mirea.network.operational.support.system.info.api.dictionary.NodeDTOList;
import ru.mirea.network.operational.support.system.info.mapper.EntityMapper;
import ru.mirea.network.operational.support.system.info.repository.ClientRepository;
import ru.mirea.network.operational.support.system.info.repository.NodeRepository;
import ru.mirea.network.operational.support.system.info.repository.PortTypeRepository;
import ru.mirea.network.operational.support.system.info.service.DictionaryService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    private final NodeRepository nodeRepository;
    private final ClientRepository clientRepository;
    private final PortTypeRepository portTypeRepository;
    private final EntityMapper entityMapper;

    @Override
    public FindNodeRs findNode(String query) {
        if (StringUtils.isBlank(query)) {
            return FindNodeRs.builder()
                    .body(NodeDTOList.builder()
                            .nodes(entityMapper.mapNodeDictionary(nodeRepository.findAll()))
                            .build())
                    .success(true)
                    .build();
        }
        return FindNodeRs.builder()
                .body(NodeDTOList.builder()
                        .nodes(entityMapper.mapNodeDictionary(nodeRepository.findByNameContains(query)))
                        .build())
                .success(true)
                .build();
    }

    @Override
    public FindCapacityRs findCapacity(String query) {
        if (StringUtils.isBlank(query)) {
            return FindCapacityRs.builder()
                    .success(true)
                    .body(CapacityDTOList.builder()
                            .portTypes(entityMapper.mapCapacityDictionary(portTypeRepository.findAll()))
                            .build())
                    .build();
        }
        return FindCapacityRs.builder()
                .success(true)
                .body(CapacityDTOList.builder()
                        .portTypes(entityMapper.mapCapacityDictionary(portTypeRepository.findAllByQuery(query)))
                        .build())
                .build();
    }

    @Override
    public FindClientRs findClient(UUID employeeId, String query) {
        if (StringUtils.isBlank(query)) {
            return FindClientRs.builder()
                    .body(ClientDTOList.builder()
                            .clients(entityMapper.mapClientDictionary(clientRepository.findAllByEmployeeId(employeeId)))
                            .build())
                    .success(true)
                    .build();
        }

        List<String> params = List.of(query.split(" "));

        if (params.size() > 3) {
            return FindClientRs.builder()
                    .success(false)
                    .error(ErrorDTO.builder()
                            .code(ErrorCode.VALIDATION_ERROR_CODE.getCode())
                            .title("Неверный формат ФИО")
                            .build())
                    .build();
        }

        Specification<ClientEntity> spec = (root, q, builder) -> {
            Subquery<Integer> subquery = q.subquery(Integer.class);
            Root<EmployeeToClientEntity> rootSubquery = subquery.from(EmployeeToClientEntity.class);
            subquery.select(builder.literal(1))
                    .where(
                            builder.or(
                                    builder.equal(rootSubquery.get("employeeId"), employeeId),
                                    builder.equal(rootSubquery.get("clientId"), root.get("id"))
                            )
                    );


            if (params.size() == 3) {
                return builder.and(
                        builder.exists(subquery),
                        builder.like(root.get("lastName"), "%" + params.get(0) + "%"),
                        builder.like(root.get("firstName"), "%" + params.get(1) + "%"),
                        builder.like(root.get("middleName"), "%" + params.get(2) + "%")
                );
            }
            if (params.size() == 2) {
                return builder.and(
                        builder.exists(subquery),
                        builder.like(root.get("lastName"), "%" + params.get(0) + "%"),
                        builder.like(root.get("firstName"), "%" + params.get(1) + "%")
                );
            }
            return builder.and(
                    builder.exists(subquery),
                    builder.like(root.get("lastName"), "%" + params.get(0) + "%")
            );
        };

        return FindClientRs.builder()
                .body(ClientDTOList.builder()
                        .clients(entityMapper.mapClientDictionary(clientRepository.findAll(spec)))
                        .build())
                .success(true)
                .build();
    }
}
