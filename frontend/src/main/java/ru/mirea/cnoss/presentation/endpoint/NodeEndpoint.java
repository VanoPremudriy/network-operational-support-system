package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.model.node.NodeService;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class NodeEndpoint {

    private final NodeService nodeService;

    public void getNode(String id, String token) {
        nodeService.getNodeById(id, token);
    }
}
