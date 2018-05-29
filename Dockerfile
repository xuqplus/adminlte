FROM alpine:3.5

COPY target/*.jar /app.jar

#curl -sL https://get.docker.com/builds/Linux/x86_64/docker-1.8.1 > /usr/bin/docker
#curl -sL https://github.com/docker/docker/raw/master/hack/dind > /usr/local/bin/dind
COPY docker /usr/bin/
COPY dind /usr/local/bin/

RUN apk --update add openjdk8-jre bash docker

EXPOSE 18080

ENV adminlte_db_host 172.17.0.9
ENV adminlte_db_url jdbc:mysql://172.17.0.9:3306/
ENV adminlte_db_username test
ENV adminlte_db_password 123456
ENV DOCKER_HOST unix:///var/run/docker.sock
ENV adminlte_docker_nginx adminlte-nginx
ENV adminlte_docker_openvpn  adminlte-openvpn

CMD java -jar app.jar