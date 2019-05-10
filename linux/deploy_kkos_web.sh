#!/bin/bash

DEPLOY_PATH=/apache/apache24/www_kkos/KKOS
SOURCE_PATH=/home/jenkins/.jenkins/workspace/KKOS_WAS_TEST_BUILD/target/KKOS/resources
#SOURCE_FILE=KKOS.war
BACKUP_PATH=/home/apache/deploy_history/kkos
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_TARGET=/apache/apache24/www_kkos/KKOS/resources
BACKUP_FILE=resources_$DATE.tar.gz

echo "======== start deploy PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="
echo "scp -r jenkins@10.217.47.145:${SOURCE_PATH} ${DEPLOY_PATH}"
scp -r jenkins@10.217.47.145:${SOURCE_PATH} ${DEPLOY_PATH}
echo "======== end   deploy PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="

echo "======== start deploy_history backup PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="
echo "cd ${DEPLOY_PATH}"
echo "tar -zcf ${BACKUP_FILE} *"
echo "mv ${DEPLOY_PATH}/${BACKUP_FILE} ${BACKUP_PATH}"
cd ${DEPLOY_PATH}
tar -zcf ${BACKUP_FILE} *
mv ${DEPLOY_PATH}/${BACKUP_FILE} ${BACKUP_PATH}
echo "======== end   deploy_history backup PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="
