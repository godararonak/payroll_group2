FROM openjdk:22

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} sampleapp.jar

ENTRYPOINT ["java","-jar","/sampleapp.jar"]

EXPOSE 8383