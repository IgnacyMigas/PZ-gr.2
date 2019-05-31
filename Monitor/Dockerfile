FROM openjdk:8-jre-slim

WORKDIR /app

COPY . /app

RUN apt-get update
RUN apt-get install -y maven 
RUN apt-get install -y openjdk-8-jdk 


RUN mvn clean
RUN mvn install

EXPOSE 8080

CMD ["java", "-jar", "target/monitor-0.0.1-SNAPSHOT.jar"]