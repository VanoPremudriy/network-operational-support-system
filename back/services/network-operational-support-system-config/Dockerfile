FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
EXPOSE 8888
COPY ${JAR_FILE} config.jar
ENTRYPOINT ["java", "-jar", "/config.jar"]