FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/product-app.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]