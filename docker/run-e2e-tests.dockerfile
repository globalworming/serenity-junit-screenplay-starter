FROM maven:3.6.0-jdk-11-slim as build
WORKDIR /workspace

COPY pom.xml .
COPY src/test src/test
COPY serenity.remote.properties serenity.properties
RUN mvn compiler:testCompile

FROM build
CMD ["mvn", "verify"]
