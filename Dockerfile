FROM maven:3.9.8-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /targer/Weather-App-0.0.1-SNAPSHOT.jar Weather-App.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","demo.jar" ]