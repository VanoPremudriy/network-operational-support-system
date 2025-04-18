package ru.mirea.cnoss.model.geodata;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "geoClient", url = "https://raw.githubusercontent.com")
public interface GeoDataService {

    @GetMapping("/codeforgermany/click_that_hood/main/public/data/russia.geojson")
    String fetchRussiaGeoJson(); // можно вернуть Map или кастомный DTO
}


//@FeignClient(name = "geoClient", url = "https://raw.githubusercontent.com")
//public interface GeoDataService {
//
//    @GetMapping("/timurkanaz/Russia_geojson_OSM/master/GeoJson's/Countries/Russia_regions.geojson")
//    String fetchRussiaGeoJson(); // можно вернуть Map или кастомный DTO
//}