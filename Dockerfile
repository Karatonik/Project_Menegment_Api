FROM openjdk:17
ADD target/project-menegment-api.jar project-menegment-api.jar
COPY ${JAR_FILE} API.jar
ENTRYPOINT ["java","-jar","project-menegment-api.jar"]
EXPOSE 8080