FROM openjdk:17

WORKDIR /app

COPY /target/consumer-1.0.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]