FROM maven:3-eclipse-temurin-17 AS builder

WORKDIR /opt/app
COPY pom.xml .
RUN mvn -X dependency:go-offline -DexcludeReactor=false
COPY src src
RUN mvn -X package


FROM eclipse-temurin:21-jre
COPY --from=builder /opt/app/src/main/resources/* .
COPY --from=builder /opt/app/target/java-backend*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","server", "/openspaceapp.yaml"]