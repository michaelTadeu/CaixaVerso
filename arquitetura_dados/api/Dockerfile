# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY .. /src
RUN mvn -q -pl api -am -DskipTests package

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/api/target/api-*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
