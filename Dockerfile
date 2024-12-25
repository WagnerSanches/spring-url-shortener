FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/URL-Shortener.jar .

ENV DB_URL      = localhost
ENV DB_USERNAME = user
ENV DB_PASSWORD = pass

EXPOSE 8080

CMD ["java", "-jar", "-Durl=${DB_URL}", "-Dusername=${USERNAME}", "-Dpassword=${PASSWORD}", "URL-Shortener.jar"]