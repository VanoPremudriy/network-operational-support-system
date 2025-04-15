package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import ru.mirea.cnoss.model.geodata.GeoDataService;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class GeoEndpoint {

    private final GeoDataService geoDataService;

    public String getRussianGeoData() {
        return geoDataService.fetchRussiaGeoJson();
    }
}