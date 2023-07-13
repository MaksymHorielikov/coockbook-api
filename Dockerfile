FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/cookbook-rest-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} myapp.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]
