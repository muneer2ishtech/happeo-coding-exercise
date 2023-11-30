FROM openjdk:21

VOLUME /tmp

EXPOSE 8080

COPY target/happeo-coding-excercise-0.0.1-SNAPSHOT.jar happeo-coding-excercise.jar

ENTRYPOINT ["java","-jar","/happeo-coding-excercise.jar"]
