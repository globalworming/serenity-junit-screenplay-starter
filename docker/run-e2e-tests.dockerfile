FROM maven:3.6.0-jdk-11-slim as gather
WORKDIR /workspace

COPY pom.xml .
COPY src/test src/test
COPY serenity.remote.properties serenity.properties

FROM gather as gather-dependencies
RUN mvn verify -Dit.test=com.example.e2e.hello.HelloWorldIT || true
RUN rm -rf target/site/serenity

FROM gather-dependencies as run
RUN mvn verify -Dit.test=com.example.e2e.hello.HelloWorldIT || true
CMD ["ls", "-al", "/workspace/target/site"]

FROM lipanski/docker-static-website:latest as serve-report
COPY --from=run /workspace/target/site/serenity /home/static
#CMD ["ls", "-al", "/home/static"]
CMD ["/thttpd", "-D", "-h", "0.0.0.0", "-p", "3000", "-d", "/home/static", "-u", "static", "-l", "-", "-M", "60"]
# TODO 