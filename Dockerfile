# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven project files (pom.xml and src) into the container
COPY pom.xml /app
COPY src /app/src

# Install Maven and build the project
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests  # Skip tests during build for faster execution

# Expose the port your app will run on (using default 1111 or $PORT if set)
EXPOSE 1111

# Run the Java application with environment variable support for port
CMD ["java", "-Dserver.port=$PORT", "-jar", "target/Portfolios-0.0.1-SNAPSHOT.war"]
