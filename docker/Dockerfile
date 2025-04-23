# Stage 1: Build app with Gradle
FROM gradle:8.6-jdk21 as builder
WORKDIR /app

COPY . .

RUN gradle build -x test

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL authors="Grzesiu"
