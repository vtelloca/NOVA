FROM openjdk:15
ARG JAR_FILE=target/*.jar 
COPY ${JAR_FILE} mytasks.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/mytasks.jar"]dir