FROM registry.access.redhat.com/ubi8/openjdk-17:1.15

# Set the working directory in the container
WORKDIR /app

# Copy the application artifact into the container
COPY target/quarkus-app /app

# Explicitly copy application.properties if needed
COPY src/main/resources/application.properties /app/application.properties

# Expose the port your application will run on
EXPOSE 8080

# Specify the custom config location to ensure Quarkus picks it up
CMD ["java", "-Dquarkus.config.location=/app/application.properties", "-jar", "quarkus-run.jar"]
