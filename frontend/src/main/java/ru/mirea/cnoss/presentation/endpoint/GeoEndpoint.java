package ru.mirea.cnoss.presentation.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import ru.mirea.cnoss.model.geodata.GeoDataService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class GeoEndpoint {

    private final GeoDataService geoDataService;

    public String getRussianGeoData() {
        return geoDataService.fetchRussiaGeoJson();
    }

    public String getRussianDetails() {
        try (InputStream is = new ClassPathResource("export.geojson").getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось прочитать russia.geojson", e);
        }
    }
}