# Multi-stage build for Spring Boot application

# Build stage
FROM eclipse-temurin:23-jdk AS build

# Set working directory for build
WORKDIR /app

# Copy Maven wrapper files
COPY mvnw ./
COPY .mvn/ .mvn/

# Make Maven wrapper executable
RUN chmod +x mvnw

# Copy and download dependencies
COPY pom.xml ./
RUN ./mvnw dependency:go-offline

# Build application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:23-jre

# Set working directory for runtime
WORKDIR /app

# Copy built application from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
