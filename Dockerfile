FROM amazoncorretto:11
ADD target/projectlbd-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar projectlbd-0.0.1-SNAPSHOT.jar