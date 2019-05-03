#!/bin/bash

docker run -d -p 8002:8080 \
  --restart="on-failure:5" \
  --name chartmuseum \
  -v /charts:/charts:z \
  -e DEBUG=true \
  -e STORAGE=local \
  -e STORAGE_LOCAL_ROOTDIR=/charts \
  ktis-bastion01.container.ipc.kt.com:5000/herasoo/chartmuseum:v0.8.3
