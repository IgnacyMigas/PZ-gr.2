FROM openjdk:8-jre-slim

WORKDIR /app

COPY . /app

RUN apt update && apt install -y maven && apt install -y openjdk-8-jdk && rm -rf /var/lib/apt/lists/*
RUN mvn clean
RUN mvn install

EXPOSE 8080

CMD ["java", "-jar", "target/monitor-0.0.1-SNAPSHOT.jar"]