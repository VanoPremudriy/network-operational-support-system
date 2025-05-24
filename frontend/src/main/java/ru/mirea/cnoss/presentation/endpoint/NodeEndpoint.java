package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.service.node.NodeService;
import ru.mirea.cnoss.service.node.dto.DetailedNodeResponse;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class NodeEndpoint {

    private final NodeService nodeService;

    private final static String BEARER = "Bearer ";

    public DetailedNodeResponse getNode(String id, String token) {

        token = BEARER + token;

        DetailedNodeResponse nodeById = nodeService.getNodeById(id, token);

        System.out.println(nodeById.getBody().getId());
        System.out.println(nodeById.getBody().getName());

        return nodeById;
    }
}
