# Use an official Maven image to build the Spring Boot app
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use Eclipse Temurin image to run the application
FROM eclipse-temurin:21-jdk-noble

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/chi_daram-0.0.1-SNAPSHOT.jar .

# Expose port 8081
EXPOSE 8081

# Specify the command to run the application
ENTRYPOINT ["java", "-Xmx300m", "-jar", "/app/chi_daram-0.0.1-SNAPSHOT.jar"]