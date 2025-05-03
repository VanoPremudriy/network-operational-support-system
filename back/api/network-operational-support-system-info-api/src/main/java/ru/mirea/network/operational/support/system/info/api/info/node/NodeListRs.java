package ru.mirea.network.operational.support.system.info.api.info.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.mirea.network.operational.support.system.common.api.BaseRs;

@Data
@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class NodeListRs extends BaseRs {
    private NodeList body;
}
