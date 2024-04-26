FROM openjdk:21

WORKDIR /app

COPY target/CloudCategorizerS3.jar CloudCategorizerS3.jar

EXPOSE 8080

CMD ["java", "jar", "CloudCategorizerS3.jar"]