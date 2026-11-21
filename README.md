# Enterprise Notification Platform

Phase 0 establishes the Java 17 / Spring Boot 3 application foundation for a
multi-channel notification platform.

## Project structure

```text
src/
└── main/
    ├── java/com/enterprise/notification/
    │   ├── NotificationPlatformApplication.java
    │   ├── config/       # OpenAPI and application configuration
    │   ├── controller/  # HTTP endpoints
    │   ├── dto/         # Request and response models
    │   ├── entity/      # JPA entities
    │   ├── exception/   # API response and global exception handling
    │   ├── mapper/      # DTO/entity mapping
    │   ├── repository/  # Spring Data repositories
    │   ├── service/     # Application and domain services
    │   └── util/        # Shared utilities
    └── resources/
        └── application.yml
pom.xml
```

## Configuration

PostgreSQL connects to `jdbc:postgresql://localhost:5432/notification_platform`
by default. Override connection details with `DB_URL`, `DB_USERNAME`, and
`DB_PASSWORD`. The default password is a local-development placeholder; set a
real secret through the environment before running outside local development.
Hibernate schema management uses `validate`, so schema changes should be
managed explicitly.

Swagger UI is available at `/swagger-ui.html` and the OpenAPI document at
`/api-docs`.

Build and run with `mvn spring-boot:run` (requires PostgreSQL to be available).
