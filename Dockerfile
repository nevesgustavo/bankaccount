FROM adoptopenjdk:11-jre-hotspot
ADD target/bankaccount.jar bankaccount.jar
EXPOSE 8082
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "-jar", "bankaccount.jar"]