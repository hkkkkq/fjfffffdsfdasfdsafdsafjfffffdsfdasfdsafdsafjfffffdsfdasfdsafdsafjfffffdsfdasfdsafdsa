#!/bin/bash

docker run -d -p 8001:8080 --restart="on-failure:5" --name registry-web -e REGISTRY_URL=https://ktis-bastion01.container.ipc.kt.com:5000/v2 -e REGISTRY_TRUST_ANY_SSL=true -e REGISTRY_READONLY=false -e REGISTRY_NAME=ktis-bastion01.container.ipc.kt.com:5000 ktis-bastion01.container.ipc.kt.com:5000/herasoo/registry-web:v20190208
