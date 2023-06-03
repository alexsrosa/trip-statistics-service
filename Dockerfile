FROM openjdk:17-jdk-alpine
ADD target/transport-statistics-1.0.0.jar transport-statistics-1.0.0.jar
ENTRYPOINT ["java", "-jar","transport-statistics-1.0.0.jar"]
EXPOSE 8080