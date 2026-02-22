package com.skyscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "HoenScanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment) throws Exception {
        ObjectMapper mapper = environment.getObjectMapper();

        List<SearchResult> rentalCars = mapper.readValue(
            getClass().getClassLoader().getResource("rental_cars.json"),
            new TypeReference<List<SearchResult>>() {
            }
        );

        List<SearchResult> hotels = mapper.readValue(
            getClass().getClassLoader().getResource("hotels.json"),
            new TypeReference<List<SearchResult>>() {
            }
        );

        List<SearchResult> searchResults = new ArrayList<>(rentalCars);
        searchResults.addAll(hotels);

        final SearchResource resource = new SearchResource(searchResults);
        environment.jersey().register(resource);
    }
}
