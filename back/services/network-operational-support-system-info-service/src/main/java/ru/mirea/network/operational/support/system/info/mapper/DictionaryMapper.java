package ru.mirea.network.operational.support.system.info.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import ru.mirea.network.operational.support.system.db.entity.ClientEntity;
import ru.mirea.network.operational.support.system.db.entity.NodeEntity;
import ru.mirea.network.operational.support.system.db.entity.PortTypeEntity;
import ru.mirea.network.operational.support.system.info.api.dictionary.CapacityDTO;
import ru.mirea.network.operational.support.system.info.api.dictionary.ClientDTO;
import ru.mirea.network.operational.support.system.info.api.dictionary.NodeDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DictionaryMapper {
    NodeDTO map(NodeEntity node);

    List<NodeDTO> mapNode(List<NodeEntity> nodes);

    CapacityDTO map(PortTypeEntity node);

    List<CapacityDTO> mapCapacity(List<PortTypeEntity> nodes);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    ClientDTO map(ClientEntity node);

    List<ClientDTO> mapClient(List<ClientEntity> nodes);

    @Named("getFullName")
    default String getFullName(ClientEntity clientEntity) {
        return clientEntity.getLastName() + " " + clientEntity.getFirstName() + " " + clientEntity.getMiddleName();
    }
}
