FROM eclipse-temurin:24-jdk AS build
WORKDIR /workspace
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
COPY microservices/pom.xml microservices/pom.xml
COPY microservices/micro-sender/pom.xml microservices/micro-sender/pom.xml
COPY microservices/micro-recipient/pom.xml microservices/micro-recipient/pom.xml
COPY microservices/micro-collector/pom.xml microservices/micro-collector/pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn -f microservices/pom.xml -q -DskipTests package || true
COPY microservices/ microservices/
RUN --mount=type=cache,target=/root/.m2 mvn -f microservices/micro-collector/pom.xml -q -DskipTests package

FROM eclipse-temurin:24-jre
ENV JAVA_OPTS=""
WORKDIR /app
COPY --from=build /workspace/microservices/micro-collector/target/micro-collector-*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
