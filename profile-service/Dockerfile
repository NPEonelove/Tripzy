# Этап сборки
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true

# Этап выполнения
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Переменные окружения
ENV SERVER_PORT=8083
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://profile_db:5432/profiles
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=1
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]