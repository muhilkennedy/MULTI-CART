FROM openjdk:17-alpine
ADD  /service/target/service-0.0.1-SNAPSHOT.jar service-0.0.1-SNAPSHOT.jar
expose 8081
ENTRYPOINT [ "java","-jar","service-0.0.1-SNAPSHOT.jar" ]