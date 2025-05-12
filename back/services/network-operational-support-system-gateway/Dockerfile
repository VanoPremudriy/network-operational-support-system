FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
EXPOSE 8085
COPY ${JAR_FILE} gateway.jar
ENTRYPOINT ["java", "-jar", "/gateway.jar"]