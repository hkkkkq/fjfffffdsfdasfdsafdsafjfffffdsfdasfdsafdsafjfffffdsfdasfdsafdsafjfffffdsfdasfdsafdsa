#!/bin/bash

sudo docker run -d -p 8002:8080 \
  --restart="on-failure:5" \
  --name chartmuseum \
  --user root \
  -v /charts:/charts \
  -e DEBUG=true \
  -e STORAGE=local \
  -e STORAGE_LOCAL_ROOTDIR=/charts \
  ktis-bastion01.container.ipc.kt.com:5000/herasoo/chartmuseum:v0.8.3
