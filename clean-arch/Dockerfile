FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim
VOLUME /tmp
COPY target/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]