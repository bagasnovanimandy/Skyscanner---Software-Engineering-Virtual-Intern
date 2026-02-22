# HoenScanner Dropwizard Microservice

HoenScanner is a Java Dropwizard microservice that exposes a JSON API for searching travel inventory by city. The service loads hotel and rental car data from local resource files and returns unified search results through a clean REST endpoint.

## Project Overview

This project is designed as a focused backend service that demonstrates practical microservice patterns:

- A Dropwizard application bootstraps the runtime and HTTP server.
- Data is read at startup from `rental_cars.json` and `hotels.json`.
- JSON is deserialized into typed domain models (`SearchResult`) using Jackson.
- A resource layer (`SearchResource`) handles input validation and filtering logic.
- A search request (`Search`) is matched against in-memory data to return relevant results.

### High-Level Architecture

1. `HoenScannerApplication` starts the service and loads datasets.
2. A combined in-memory list of `SearchResult` objects is created.
3. `SearchResource` is registered with Jersey.
4. Clients call `POST /search` with a city payload.
5. Matching results are returned as JSON.

## Tech Stack

- Java
- Dropwizard
- Jackson (`ObjectMapper`)
- Jakarta REST (`jakarta.ws.rs`)
- Jakarta Validation (`jakarta.validation`)
- Maven

## API Documentation

### Search Endpoint

- Method: `POST`
- Path: `/search`
- Content-Type: `application/json`
- Response Type: `application/json`

#### Request Body

```json
{
  "city": "petalborough"
}
```

#### Successful Response (`200 OK`)

```json
[
  {
    "city": "petalborough",
    "kind": "rental_car",
    "title": "Petalborough City Cars"
  },
  {
    "city": "petalborough",
    "kind": "hotel",
    "title": "Petalborough Grand Hotel"
  }
]
```

#### Behavior Notes

- City matching is case-insensitive.
- Leading/trailing spaces in city input are ignored.
- If city is `null` or blank, the service returns an empty list.

## Running the Service

### Prerequisites

- Java 17+ (or the version required by the project `pom.xml`)
- Maven 3.8+

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/<artifact-name>.jar server config.yml
```

Replace `<artifact-name>` with the generated JAR in `target/`.

### Quick API Check

```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d "{\"city\":\"petalborough\"}"
```

## Testing Instructions

Run all tests:

```bash
mvn test
```

Optional validation flow:

1. Start the service locally.
2. Send requests with exact case, mixed case, and extra whitespace in `city`.
3. Verify only matching hotel/rental entries are returned.
4. Verify null/blank city returns `[]`.

## Engineering Highlights

- Clean separation of concerns between application bootstrap, models, and resources.
- Dropwizard-managed `ObjectMapper` usage for framework-consistent JSON handling.
- Stream-based filtering with null-safe logic.
- Jakarta namespace adoption (`jakarta.ws.rs`, `jakarta.validation`) for modern compatibility.
- Readable, minimal API design with strongly typed request/response models.

## Portfolio Value

This project demonstrates real-world backend engineering skills: designing a focused REST API, loading and transforming data, applying validation and defensive coding, and producing maintainable service code using industry-standard Java tooling. It is a strong portfolio artifact for backend and microservice roles.
