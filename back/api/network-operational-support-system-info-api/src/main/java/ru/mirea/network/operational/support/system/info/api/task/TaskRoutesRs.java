package ru.mirea.network.operational.support.system.info.api.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.common.api.BaseRs;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Jacksonized
public class TaskRoutesRs extends BaseRs {
    @Schema(description = "Номер страницы", example = "1")
    private Integer numberOfPages;

    private List<Route> routes;
}