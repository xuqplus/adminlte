FROM alpine:3.5

COPY target/*.jar /app.jar

RUN apk --update add openjdk8-jre bash

EXPOSE 8080

ENV adminlte_db_host 172.17.0.9
ENV adminlte_db_url jdbc:mysql://172.17.0.9:3306/
ENV adminlte_db_username test
ENV adminlte_db_password 123456

CMD java -jar app.jar