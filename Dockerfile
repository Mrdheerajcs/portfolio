# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven project files (pom.xml) and source code (src)
COPY pom.xml /app
COPY src /app/src

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build the project using Maven
RUN mvn clean install

# Expose the port your app will run on (Render uses dynamic port, we expose $PORT)
EXPOSE $PORT

# Run the Java application (using $PORT for flexibility in Render environment)
CMD ["java", "-Dserver.port=$PORT", "-jar", "target/Portfolios-0.0.1-SNAPSHOT.war"]
