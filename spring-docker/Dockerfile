FROM openjdk:17

WORKDIR /app

COPY /target/spring-docker-1.0.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8000