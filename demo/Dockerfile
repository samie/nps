# First stage: JDK build
FROM openjdk:17 AS build

# Update package lists and Install Maven
RUN microdnf update -y && \
    microdnf install -y maven && \
    microdnf clean all

WORKDIR /usr/src/app

# Copy the Maven dependencies.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the application source code
COPY src/ ./src/
COPY frontend/ ./frontend/

## Do the build
RUN mvn clean install -Pproduction

# Second stage: Lightweight jdk-slim image
FROM openjdk:17-jdk-slim
RUN useradd -m appuser

RUN mkdir /app && \
    chown -R appuser /app

USER appuser

WORKDIR /app

# Copy the native binary from the build stage
COPY --from=build /usr/src/app/target/*.jar /app/app.jar

# Run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]