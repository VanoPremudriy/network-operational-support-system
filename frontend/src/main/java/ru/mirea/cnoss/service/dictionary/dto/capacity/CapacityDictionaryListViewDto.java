package ru.mirea.cnoss.service.dictionary.dto.capacity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CapacityDictionaryListViewDto {
    private List<CapacityDictionaryViewDto> portTypes;
}