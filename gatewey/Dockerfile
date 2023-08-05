FROM amazoncorretto:11-alpine-jdk
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]