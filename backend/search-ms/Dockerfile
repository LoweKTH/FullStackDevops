# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use an OpenJDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/quarkus-app /app

# Expose the port your application will run on
# Change this to the port your Quarkus app is configured to use
EXPOSE 8080

# Run the Java application
# For Quarkus, use the runner JAR which includes the main class configuration
CMD ["java", "-jar", "quarkus-run.jar"]