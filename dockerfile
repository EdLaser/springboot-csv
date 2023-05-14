FROM eclipse-temurin:20
VOLUME /tmp
COPY csv-upload/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]