FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
EXPOSE 8089
COPY ${JAR_FILE} info-service.jar
ENTRYPOINT ["java", "-jar", "/info-service.jar"]