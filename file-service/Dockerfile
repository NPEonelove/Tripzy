FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV SERVER_PORT=8082
ENV YANDEX_S3_ACCESS_KEY=your-access-key
ENV YANDEX_S3_SECRET_KEY=your-secret-key
ENV YANDEX_S3_BUCKET=your-bucket-name
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka
ENV EUREKA_INSTANCE_LEASE_RENEWAL_INTERVAL_IN_SECONDS=5
ENV EUREKA_INSTANCE_LEASE_EXPIRATION_DURATION_IN_SECONDS=10

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]