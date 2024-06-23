FROM openjdk:11

COPY target/mydut-backend-0.0.1-SNAPSHOT.jar mydut-backend.jar

ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Bangkok", "-jar", "mydut-backend.jar"]