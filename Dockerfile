FROM openjdk:17
ADD target/Lab1.jar Lab1.jar
ENTRYPOINT ["java", "-jar", "Lab1.jar"]
EXPOSE 8080