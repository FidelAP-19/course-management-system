# Use Java 21 base image (lightweight Alpine Linux)
FROM eclipse-temurin:21-jdk-alpine

# Set working directory in container
WORKDIR /app

# Copy the JAR file from target/ into container
COPY target/*.jar app.jar

# Expose port 8080 (documentation only, doesn't actually open port)
EXPOSE 8080

# Command to run when container starts
ENTRYPOINT ["java", "-jar", "app.jar"]

