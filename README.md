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
build.gradle
settings.gradle
```

## Configuration

PostgreSQL connects to `jdbc:postgresql://localhost:5432/notification_platform`
by default. Start the local database with `docker compose up -d postgres`; the
Compose service creates the database and user expected by the app. Override
connection details with `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`. The default
password is for local development only; set a real secret through the
environment outside local development. Hibernate schema management uses
`validate`, so schema changes should be managed explicitly.

Swagger UI is available at `/swagger-ui.html` and the OpenAPI document at
`/api-docs`.

Build and test with `gradle test`, or run with `gradle bootRun` after PostgreSQL
is healthy. In IntelliJ IDEA, reload the Gradle project and
run the Gradle `bootRun` task, or select the `notification-platform.main` module
for an Application run configuration. Set the Gradle JVM to JDK 17.
