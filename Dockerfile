# Build stage with Maven and Java 8
FROM maven:3.6.3-jdk-8 as build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
COPY checkstyle-suppressions.xml /app
RUN mvn clean package

# Final stage with Java 8 JRE
FROM openjdk:8-jre-slim
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]