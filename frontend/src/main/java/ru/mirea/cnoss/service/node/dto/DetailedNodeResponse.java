package ru.mirea.cnoss.service.node.dto;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

@Getter
@Setter
public class DetailedNodeResponse extends BaseResponse {
    private DetailedNode body;
}