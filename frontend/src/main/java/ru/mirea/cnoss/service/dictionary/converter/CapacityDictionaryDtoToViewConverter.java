package ru.mirea.cnoss.service.dictionary.converter;

import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.dictionary.dto.capacity.CapacityDictionaryDto;
import ru.mirea.cnoss.service.dictionary.dto.capacity.CapacityDictionaryViewDto;

@Component
public class CapacityDictionaryDtoToViewConverter {

    public CapacityDictionaryViewDto convert(CapacityDictionaryDto dto) {
        CapacityDictionaryViewDto viewDto = new CapacityDictionaryViewDto();
        viewDto.setCapacity(dto.getCapacity().toString());
        return viewDto;
    }
}