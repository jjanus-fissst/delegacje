FROM amazoncorretto:11
ADD target/project-lbd-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar project-lbd-0.0.1-SNAPSHOT.jar