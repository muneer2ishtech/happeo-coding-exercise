FROM openjdk:21

VOLUME /tmp

EXPOSE 8080

COPY target/happeo-coding-exercise-0.0.1-SNAPSHOT.jar happeo-coding-exercise.jar

ENTRYPOINT ["java","-jar","/happeo-coding-exercise.jar"]