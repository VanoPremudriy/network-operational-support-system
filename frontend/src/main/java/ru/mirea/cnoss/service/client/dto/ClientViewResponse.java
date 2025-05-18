package ru.mirea.cnoss.service.client.dto;

import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

import java.util.Set;

@Getter
@Setter
public class ClientViewResponse extends BaseResponse {

    @Nullable
    private Integer numberOfPages;

    @Nullable
    private Set<ClientViewDto> clients;
}