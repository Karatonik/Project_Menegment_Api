FROM openjdk:17
ENV _JAVA_OPTIONS="-Xmx200m"
COPY target/ProjectManagement-0.0.1-SNAPSHOT.jar ProjectManagement-0.0.1-SNAPSHOT.jar 
CMD java  -jar ProjectManagement-0.0.1-SNAPSHOT.jar
EXPOSE 8080