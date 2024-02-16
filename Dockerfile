FROM openjdk:23
COPY  target/Application-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]