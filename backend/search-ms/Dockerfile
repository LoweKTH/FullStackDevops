# Use a base image that matches your project's Java version
FROM registry.access.redhat.com/ubi8/openjdk-17:1.15

# Set the working directory in the container
WORKDIR /app

# Copy the application artifact into the container
# This assumes you're using the Quarkus "legacy-jar" packaging or default "quarkus-app" directory
COPY target/quarkus-app /app

# Expose the port your application will run on
EXPOSE 8080

# Specify the QUARKUS_PROFILE if needed (e.g., dev, prod)
# This ensures the right application.properties file is used
ENV QUARKUS_PROFILE=dev

# Run the Java application
# Ensure the correct JAR is used to run your application
CMD ["java", "-jar", "/app/quarkus-run.jar"]
