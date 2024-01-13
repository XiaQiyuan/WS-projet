

# --------------
# BUILD JRE STAGE
# --------------
FROM alpine as build-jre
RUN apk add --no-cache openjdk17
RUN apk add --no-cache binutils

# create a minimal JRE
RUN jlink \
    --add-modules java.base,java.logging,java.xml,java.desktop,java.management,java.sql,java.naming,jdk.unsupported \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /jre


# --------------
# BUILD PROJECT STAGE
# --------------
FROM alpine as build-project
RUN apk add --no-cache openjdk17
RUN apk add --no-cache maven

# compile the project
WORKDIR /projet
COPY pom.xml ./
COPY src/ ./src/
RUN mvn --no-transfer-progress -B package


# --------------
# RUN STAGE
# --------------
FROM alpine

# copy JRE from build stage
ENV JAVA_HOME=/opt/java
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=build-jre /jre $JAVA_HOME

RUN addgroup -S projet && adduser -S -G projet projet
USER projet

# copy app from build stage
WORKDIR /opt/projet
COPY --from=build-project /projet/target/projet.jar .

# define a volume for the stored content
RUN mkdir content logs db
VOLUME ["/opt/projet/content", "/opt/projet/logs", "/opt/projet/db"]

# define a healthcheck
HEALTHCHECK --interval=1m --timeout=5s \
    CMD wget http://localhost:8080/health -q -O - | grep -c '{"status":"ok"}' || exit 1

# run the app
CMD ["java", "-jar", "projet.jar"]
EXPOSE 8080/tcp
