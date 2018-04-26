FROM alpine:3.5

COPY target/*.jar /app.jar

RUN apk --update add openjdk8-jre bash

EXPOSE 8080

ENV adminlte_db 172.17.0.9

CMD java -jar app.jar