FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml /app
COPY src /app/src

RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests

EXPOSE 8080
CMD ["java", "-Dserver.port=$PORT", "-jar", "target/Portfolios-0.0.1-SNAPSHOT.war"]
