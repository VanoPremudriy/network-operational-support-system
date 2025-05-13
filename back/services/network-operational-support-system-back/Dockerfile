FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} back.jar
ENTRYPOINT ["java", "-jar", "/back.jar"]