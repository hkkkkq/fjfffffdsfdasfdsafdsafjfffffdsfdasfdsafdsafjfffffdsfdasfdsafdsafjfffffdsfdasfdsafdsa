#!/bin/bash

docker run -d -p 8000:8080 --restart="on-failure:5" --name registry-browser -e DOCKER_REGISTRY_URL=https://ktis-bastion01.container.ipc.kt.com:5000 -e NO_SSL_VERIFICATION=true -e ENABLE_DELETE_IMAGES=true ktis-bastion01.container.ipc.kt.com:5000/herasoo/registry-browser:v20190208
