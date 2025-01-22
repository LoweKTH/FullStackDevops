# Use the Quarkus native image for a lightweight setup
FROM openjdk:17-jdk-slim


# Set the working directory
WORKDIR /app

# Copy the application JAR file and dependencies
COPY target/*-runner.jar app.jar

# Expose the port the service listens on
EXPOSE 8085

# Run the application
CMD ["java", "-jar", "app.jar"]