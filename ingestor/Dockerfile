FROM amazoncorretto:17

WORKDIR /application

COPY target/*.jar application.jar

ENTRYPOINT exec java -jar application.jar
