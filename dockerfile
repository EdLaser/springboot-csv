FROM eclipse-temurin:20
COPY csv-upload/target/csv-upload-1.0.0.jar csv-upload-1.0.0.jar
ENTRYPOINT ["java","-jar","/csv-upload-1.0.0.jar", "--debug"]