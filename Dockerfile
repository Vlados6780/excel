FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Xmx2g", "-Xms1500m","-jar","/app.jar"]
EXPOSE 8080