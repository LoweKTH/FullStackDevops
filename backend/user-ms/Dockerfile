# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use an OpenJDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/user-ms-0.0.1-SNAPSHOT.jar user-ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "user-ms-0.0.1-SNAPSHOT.jar"]
