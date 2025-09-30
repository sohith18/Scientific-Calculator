FROM eclipse-temurin:24-jre
WORKDIR /app
COPY target/*-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
