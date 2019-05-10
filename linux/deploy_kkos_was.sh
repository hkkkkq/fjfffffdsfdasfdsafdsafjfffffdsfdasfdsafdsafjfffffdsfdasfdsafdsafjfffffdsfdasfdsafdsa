#!/bin/bash

DEPLOY_PATH=/jboss/applications/kkosSvr11
SOURCE_PATH=/home/jenkins/.jenkins/workspace/KKOS_WAS_TEST_BUILD/target
SOURCE_FILE=KKOS.war
BACKUP_PATH=/home/jboss/deploy_history/kkos
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE=${SOURCE_FILE}_$DATE

echo "======== start deploy PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="
echo "scp jenkins@10.217.47.145:${SOURCE_PATH}/${SOURCE_FILE} ${DEPLOY_PATH}"
scp jenkins@10.217.47.145:${SOURCE_PATH}/${SOURCE_FILE} ${DEPLOY_PATH}
echo "======== end   deploy PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="

echo "======== start deploy_history backup PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="
echo "cp ${DEPLOY_PATH}/${SOURCE_FILE} ${BACKUP_PATH}/${BACKUP_FILE}"
cp ${DEPLOY_PATH}/${SOURCE_FILE} ${BACKUP_PATH}/${BACKUP_FILE}
echo "======== end   deploy_history backup PRD: "$(date +'%Y%m%d %H:%M:%S')"==========="
