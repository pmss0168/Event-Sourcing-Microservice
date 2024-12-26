# Stage 1: Use Maven for building
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY ./ApiGateway ./apigateway
WORKDIR /app/apigateway
RUN mvn clean package -DskipTests

# Staage 2: use OpenJDK for running
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/apigateway/target/*.jar apigateway.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","apigateway.jar"]