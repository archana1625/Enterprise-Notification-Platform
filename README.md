# Enterprise Notification Platform

The Java 17 / Spring Boot 3 application provides multi-channel notifications
and notification template management.

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
notification-contract/  # Shared Kafka event DTO
delivery-service/       # Independent Kafka consumer service
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

## Notification templates

Templates are managed at `/notification-templates` using `POST`, `GET`,
`GET /{id}`, `PUT /{id}`, and `DELETE /{id}`. Template requests include
`templateName`, `channel` (`EMAIL`, `SMS`, `PUSH`, or `IN_APP`),
`subjectTemplate`, `bodyTemplate`, and `active`. Subjects and bodies support
`{{variableName}}` placeholders, for example `Hello {{name}}` and
`Order {{orderId}}`.

Render a stored template with `POST /notification-templates/{id}/render` and
a JSON body such as `{"variables":{"name":"Sam","orderId":"1234"}}`. The
response contains the rendered `subject` and `body`; missing placeholder
values return HTTP 400. The template schema is created by `schema.sql`.

## Kafka notification events

Creating a notification publishes a JSON `NotificationEvent` to the
`notification-created` Kafka topic. The shared event contract is in
`notification-contract`; `delivery-service` consumes the event and logs the
notification ID and channel as its delivery-handler entry point. Consumer
failures are retried three times and then sent to `notification-created.DLT`.

Start PostgreSQL, Kafka, and the delivery service with
`docker compose up --build`. Run the notification API locally with
`gradle bootRun`; it connects to Kafka at `localhost:9092` by default.
Set `KAFKA_BOOTSTRAP_SERVERS` to override the broker address for either app.
