package ru.mirea.cnoss.service.route.dto.createRoute;

import com.vaadin.hilla.Nullable;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.cnoss.service.BaseResponse;

@Getter
@Setter
public class CreateRouteResponse extends BaseResponse {
    @Nullable
    private String description;
}