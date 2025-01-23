# Use a base image that matches your project's Java version
# For Quarkus, you might use a smaller image for efficiency
FROM registry.access.redhat.com/ubi8/openjdk-17:1.15

# Set the working directory in the container
WORKDIR /app

# Copy the application artifact into the container
# Assuming you're using the default Quarkus packaging which produces a 'quarkus-app' directory
COPY target/quarkus-app /app

# Expose the port your application will run on
# Change this to the port your Quarkus app is configured to use
EXPOSE 8080

# Run the Java application
# For Quarkus, use the runner JAR which includes the main class configuration
CMD ["java", "-jar", "quarkus-run.jar"]