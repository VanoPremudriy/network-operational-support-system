package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.presentation.utils.TokenUtils;
import ru.mirea.cnoss.service.node.NodeService;
import ru.mirea.cnoss.service.node.dto.DetailedNodeResponse;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class NodeEndpoint {

    private final NodeService nodeService;

    public DetailedNodeResponse getNode(String id) {

        String token = TokenUtils.getBearerTokenOrThrow();

        return nodeService.getNodeById(id, token);
    }
}
