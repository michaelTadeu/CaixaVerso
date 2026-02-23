# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY .. /src
RUN mvn -q -pl worker -am -DskipTests package

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/worker/target/worker-*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
