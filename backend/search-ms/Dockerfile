# Stage 1: Build the application using Maven (JVM or Native)
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download the dependencies (this caches dependencies)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and compile the app (JVM or Native)
COPY src /app/src

# Build the app, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Prepare the runtime image
FROM openjdk:17-jdk-slim AS runtime

# Set working directory in the runtime image
WORKDIR /app

# Copy the generated artifact from the build stage
COPY --from=build /app/target/search-ms-1.0-SNAPSHOT.jar /app/quarkus-app.jar

# Expose the port the app runs on (default for Quarkus is 8080)
EXPOSE 8080

# Command to run the app in JVM mode
CMD ["java", "-jar", "/app/quarkus-app.jar"]
