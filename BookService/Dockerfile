# Stage 1: Use Maven for building
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY ./BookService ./bookservice
COPY ./CommonService ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/bookservice
RUN mvn clean package -DskipTests

# Staage 2: use OpenJDK for running
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/bookservice/target/*.jar bookservice.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","bookservice.jar"]