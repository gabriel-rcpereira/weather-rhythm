FROM adoptopenjdk:11-jdk-hotspot
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]