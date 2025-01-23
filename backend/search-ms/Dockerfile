# Build stage
FROM maven:3.8.6-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and compile the app
COPY src /app/src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/*-runner.jar /app/quarkus-app.jar

# Expose the application port (default for Quarkus is 8080)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/quarkus-app.jar"]