#!/bin/bash

container=$1
if [[ -z ${container} ]]; then
    echo container is null;
    exit 1
fi

docker exec -i ${container} cat /etc/nginx/nginx.conf