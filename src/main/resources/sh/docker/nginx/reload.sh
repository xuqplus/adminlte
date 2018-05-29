#!/bin/bash

container=$1
if [[ -z ${container} ]]; then
    echo container is null;
    exit 1
fi

if [ $(docker exec -it ${container} nginx -t | grep -c fail) = 0 ]; then
    if [ $(docker exec -it ${container} nginx -t | grep -c success) != 0 ]; then
        echo nginx -t success;
        docker exec -it ${container} nginx -s reload;
        echo nginx reloaded;
        exit 0
    fi
fi

echo nginx -t fail;
exit 1