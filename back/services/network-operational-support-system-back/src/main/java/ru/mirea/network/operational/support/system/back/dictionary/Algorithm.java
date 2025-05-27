package ru.mirea.network.operational.support.system.back.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Algorithm {
    ROUTE_SEARCH("routeSearch"),
    ROUTE_SEARCH_MAXFLOW("routeSearchMaxflow"),

    ;

    private final String name;


    public static Algorithm of(String name) {
        if (name == null) {
            return null;
        }

        for (Algorithm algorithm : values()) {
            if (algorithm.getName().equals(name)) {
                return algorithm;
            }
        }
        return null;
    }
}
