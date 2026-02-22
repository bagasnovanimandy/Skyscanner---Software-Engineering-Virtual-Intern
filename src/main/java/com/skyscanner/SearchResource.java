package com.skyscanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {
    private final List<SearchResult> searchResults;

    public SearchResource(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @POST
    public List<SearchResult> search(@NotNull @Valid Search search) {
        if (search == null || search.getCity() == null) {
            return Collections.emptyList();
        }

        final String requestedCity = search.getCity().trim();
        if (requestedCity.isEmpty()) {
            return Collections.emptyList();
        }

        return searchResults.stream()
            .filter(result -> result.getCity() != null)
            .filter(result -> result.getCity().trim().equalsIgnoreCase(requestedCity))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
