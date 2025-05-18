package ru.mirea.cnoss.service.dictionary.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.cnoss.service.dictionary.dto.capacity.CapacityDictionaryListViewDto;
import ru.mirea.cnoss.service.dictionary.dto.capacity.CapacityDictionaryResponse;
import ru.mirea.cnoss.service.dictionary.dto.capacity.CapacityDictionaryViewDto;
import ru.mirea.cnoss.service.dictionary.dto.capacity.CapacityDictionaryViewResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CapacityDictionaryResponseToViewConverter {

    private final CapacityDictionaryDtoToViewConverter converter;

    public CapacityDictionaryViewResponse convert(CapacityDictionaryResponse response) {
        CapacityDictionaryViewResponse viewResponse = new CapacityDictionaryViewResponse();

        List<CapacityDictionaryViewDto> dtos = response.getBody()
                .getPortTypes()
                .stream()
                .map(converter::convert)
                .toList();

        CapacityDictionaryListViewDto listViewDto = new CapacityDictionaryListViewDto();
        listViewDto.setPortTypes(dtos);

        viewResponse.setBody(listViewDto);
        viewResponse.setError(response.getError());
        viewResponse.setSuccess(response.isSuccess());

        return viewResponse;
    }
}
