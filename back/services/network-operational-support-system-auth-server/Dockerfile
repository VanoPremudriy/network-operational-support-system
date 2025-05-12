FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
EXPOSE 8083
COPY ${JAR_FILE} auth.jar
ENTRYPOINT ["java", "-jar", "/auth.jar"]