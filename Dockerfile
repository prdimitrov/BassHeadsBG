FROM openjdk:17-jdk-alpine
MAINTAINER pesho
COPY target/bassheadsbg-0.0.1-SNAPSHOT.jar bassheadsbg-0.0.1-SNAPSHOT.jar
COPY src/main/resources /app/resources
ENTRYPOINT ["java","-jar","/bassheadsbg-0.0.1-SNAPSHOT.jar"]