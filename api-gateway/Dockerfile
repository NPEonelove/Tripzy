FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true

# Этап выполнения (runtime stage)
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV SERVER_PORT=8080
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka
ENV JWT_SECRET_KEY=default-secret-key

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]