# Build stage
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY mvnw pom.xml ./  
COPY .mvn/ .mvn/

RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the wait-for-it.sh script
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Copy the built JAR file
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Use wait-for-it.sh to wait for the database to be ready
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app/app.jar"]