# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
# Accept build arguments
ARG MYSQL_DATABASE
ARG MYSQL_ROOT_PASSWORD
ARG MYSQL_HOST
ARG MYSQL_PORT

# Set environment variables inside the container
ENV MYSQL_DATABASE=${MYSQL_DATABASE}
ENV MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
ENV MYSQL_HOST=${MYSQL_HOST}
ENV MYSQL_PORT=${MYSQL_PORT}

RUN mvn clean package -DskipTests

# Use an OpenJDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/doctorstaff-ms-0.0.1-SNAPSHOT.jar doctorstaff-ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "doctorstaff-ms-0.0.1-SNAPSHOT.jar"]
