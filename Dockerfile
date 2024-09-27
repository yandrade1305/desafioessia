FROM maven:3.9.8-eclipse-temurin-21 AS build

COPY backend/src /app/src

COPY backend/pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:21
COPY --from=build /app/target/virtufile-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
