##############################
## Build a release artifact. ##
FROM maven:3.8.3-openjdk-17-slim AS builder

WORKDIR /workspace/app
COPY pom.xml .
COPY src src

RUN mvn clean install -DskipTests

#############################
## Create image with build ##
FROM openjdk:17-jdk-alpine3.14

RUN apk --no-cache add curl

RUN addgroup -S tripstatistics
RUN adduser -S tripstatistics -G tripstatistics
USER tripstatistics:tripstatistics

ARG PROFILES_DEFAULT="default"
ENV PROFILES=$PROFILES_DEFAULT

ENV APP_HOME=/workspace/app
WORKDIR $APP_HOME

# Copy the jar to the production image from the builder stage.
COPY --from=builder /workspace/app/src/application/target/trip-statistics-application-*.jar ./trip-statistics-application.jar
EXPOSE 8080

# Run the web service on container startup.
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${PROFILES} --add-opens=java.base/java.lang=ALL-UNNAMED -jar trip-statistics-application.jar"]