# to run serenity e2e tests, we need java and maven as a base
FROM maven:3.6.0-jdk-11-slim as gather
WORKDIR /workspace

# for maven, we need a pom, our test sources, and serenity specific files
COPY pom.xml .
COPY src/test src/test
COPY serenity.remote.properties serenity.properties

# we run a minimal test to make sure the image has all the needed dependencies
FROM gather as gather-dependencies
RUN mvn verify -Dit.test=com.example.e2e.hello.HelloWorldIT || true
  # but cleanup the test results, they just bloat the image
RUN rm -rf target/site/serenity

# then run the actual tests, you may need a running selenium grid somewhere or something similar
FROM gather-dependencies as run
RUN mvn verify -Dit.test=com.example.e2e.hello.HelloWorldIT || true
# CMD ["ls", "-al", "/workspace/target/site"]
# now you have the test results you can evaluate, publish, whatever

# this should result in a running server serving the test results, but i haven't made it work yet
FROM lipanski/docker-static-website:latest as serve-report
COPY --from=run /workspace/target/site/serenity .
CMD ["/busybox", "httpd", "-f", "-v", "-p", "3000", "-c", "httpd.conf"]
