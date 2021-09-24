FROM openjdk:17-jdk-alpine
ARG JAR_FILE
ADD ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]