package ru.mirea.cnoss.model.dictionary.converter;

import org.springframework.stereotype.Component;
import ru.mirea.cnoss.model.dictionary.dto.capacity.CapacityDictionaryDto;
import ru.mirea.cnoss.model.dictionary.dto.capacity.CapacityDictionaryViewDto;

@Component
public class CapacityDictionaryDtoToViewConverter {

    public CapacityDictionaryViewDto convert(CapacityDictionaryDto dto) {
        CapacityDictionaryViewDto viewDto = new CapacityDictionaryViewDto();
        viewDto.setCapacity(dto.getCapacity().toString());
        return viewDto;
    }
}