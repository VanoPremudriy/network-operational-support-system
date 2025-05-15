package ru.mirea.cnoss.model.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.model.BaseResponse;

import java.util.Set;

@Getter
@Setter
public class ClientResponse extends BaseResponse {

    @Nullable
    @JsonProperty("numberOfPages")
    private Integer numberOfPages;

    @Nullable
    private Set<ClientDto> clients;
}