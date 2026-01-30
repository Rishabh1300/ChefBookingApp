#FROM ubuntu:latest
#LABEL authors="bonami"

#FROM eclipse-termurin:17-jdk
#
#WORKDIR /app
#
#COPY --from=builder /app/target/Order-Service-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8081
#
#ENTRYPOINT ["java","-jar","app.jar"]

#FROM maven:3.9.9-eclipse-temurin-17 AS builder
#
#WORKDIR /app
#
## Copy pom first (for dependency caching)
#COPY pom.xml .
#
#RUN mvn dependency:go-offline
#
## Copy source
#COPY src ./src
#
## Build jar
#RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar Order-Service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Order-Service.jar"]