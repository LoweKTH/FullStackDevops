# Use the Quarkus native image for a lightweight setup
FROM openjdk:17-jdk-slim


# Set the working directory
WORKDIR /app

# Copy the application JAR from the target directory
COPY target/quarkus-app/lib/ /app/lib/
COPY target/quarkus-app/*.jar /app/
COPY target/quarkus-app/app/ /app/app/
COPY target/quarkus-app/quarkus/ /app/quarkus/

# Expose the application port
EXPOSE 8085

# Set the entrypoint command to run the Quarkus app
CMD ["java", "-jar", "/app/quarkus-run.jar"]