package ru.mirea.cnoss.service.client.converter;

import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.client.dto.ClientDto;
import ru.mirea.cnoss.service.client.dto.ClientViewDto;

@Component
public class ClientDtoToViewDtoConverter {

    public ClientViewDto convert(ClientDto dto) {
        ClientViewDto viewDto = new ClientViewDto();
        viewDto.setId(dto.getId());
        viewDto.setOrganization(dto.getOrganization());
        viewDto.setFirstName(dto.getFirstName());
        viewDto.setLastName(dto.getLastName());
        viewDto.setEmail(dto.getEmail());
        viewDto.setMiddleName(dto.getMiddleName());
        return viewDto;
    }
}