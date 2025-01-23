# Use a base image that matches your project's Java version
FROM registry.access.redhat.com/ubi8/openjdk-17:1.15

# Set the working directory in the container
WORKDIR /app

# Copy only the application.properties file
COPY target/application.properties /app/application.properties

# Copy the application artifact (assuming it's a JAR file)
COPY target/*.jar /app/

# Expose the port your application will run on
EXPOSE 8080

# Specify the QUARKUS_PROFILE if needed (e.g., dev, prod)
# This ensures the right application.properties file is used
ENV QUARKUS_PROFILE=dev

# Run the Java application
# Ensure the correct JAR is used to run your application
CMD ["java", "-jar", "/app/*.jar"]