FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8082
ADD target/bankaccount.jar bankaccount.jar
ENTRYPOINT ["java", "-jar", "/bankaccount.jar"]
