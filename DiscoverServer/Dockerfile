## Stage 1: Use Maven for building
#FROM maven:3.9.9-amazoncorretto-21 AS build
#WORKDIR /app
#COPY ./DiscoverServer ./discoverserver
#WORKDIR /app/discoverserver
#RUN mvn clean package -DskipTests
#
## Stage 2: use OpenJDK for running
#FROM openjdk:21-jdk-slim
#WORKDIR /app
#COPY --from=build /app/discoverserver/target/*.jar discoverserver.jar
#EXPOSE 8761
#ENTRYPOINT ["java","-jar","discoverserver.jar"]

# Stage 1: Build with Maven
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY ./DiscoverServer ./discoverserver
WORKDIR /app/discoverserver
RUN mvn clean package -DskipTests

# Stage 2: Run with lightweight JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/discoverserver/target/*.jar discoverserver.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "discoverserver.jar"]
