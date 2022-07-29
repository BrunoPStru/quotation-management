FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotationmanagement-v1.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","quotationmanagement-v1.jar"]
