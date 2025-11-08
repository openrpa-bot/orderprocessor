FROM openjdk:26-ea-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","app.jar"]

