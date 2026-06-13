FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/*.war app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]