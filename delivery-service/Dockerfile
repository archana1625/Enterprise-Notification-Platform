FROM gradle:8.14.3-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN ./gradlew :delivery-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/delivery-service/build/libs/delivery-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
