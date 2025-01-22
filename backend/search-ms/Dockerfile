# Use Maven to build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy the project files
COPY . .

# Build the application and skip tests
RUN mvn clean package -DskipTests

# Use OpenJDK 17 for the runtime environment
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built application JAR from the build stage
COPY --from=build /app/target/*-runner.jar search-ms.jar

# Expose the port the Quarkus service listens on
EXPOSE 8085

# Run the application
ENTRYPOINT ["java", "-jar", "search-ms.jar"]
