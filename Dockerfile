# Use a valid Maven image with JDK 17
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set the working directory in the build container
WORKDIR /app

# Copy the Maven configuration file and project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use an OpenJDK runtime image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the runtime container
WORKDIR /app

# Copy the built WAR file from the build stage
COPY --from=build /app/target/*.war app.war

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.war"]
