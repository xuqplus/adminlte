#!/bin/bash

container=$1
if [[ -z ${container} ]]; then
    echo container is null;
    exit 1
fi

server_name=$2
if [[ -z ${server_name} ]]; then
    echo server_name is null
    exit 1
fi

server_ip=$3
if [[ -z ${server_ip} ]]; then
    echo server_ip is null
    exit 1
fi

server_port=$4
if [[ -z ${server_port} ]]; then
    echo server_port is null
    exit 1
fi


export domain=xuqplus.space

export conf="server {
	listen 8080;
	server_name ${server_name}.${domain};

	location / {
		proxy_pass http://${server_ip}:${server_port};
	}
}"

export confFile=${server_name}.${domain}

echo -e "${conf}" > ${confFile}
docker cp ${confFile} ${container}:/etc/nginx/sites-enabled/
rm ${confFile} > /dev/null


if [ $(docker exec -it ${container} nginx -t | grep -c fail) != 0 ]; then
    if [ $(docker exec -it ${container} nginx -t | grep -c success) -eq 0 ]; then
        docker exec -it ${container} rm /etc/nginx/sites-enabled/${confFile}
        echo ${confFile} nginx -t failed, deleted;
    fi
fi

if [ $(docker exec -it ${container} nginx -t | grep -c fail) = 0 ]; then
    if [ $(docker exec -it ${container} nginx -t | grep -c success) != 0 ]; then
        echo ${confFile} nginx -t success;
    fi
fi
