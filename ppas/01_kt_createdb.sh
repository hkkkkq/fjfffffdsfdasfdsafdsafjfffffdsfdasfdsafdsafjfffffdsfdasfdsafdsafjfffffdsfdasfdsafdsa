#!/bin/sh

if [ $# -ne 1 ]; then
    echo "Usage: $0 DATABASE_NAME"
    exit;
fi;

WORKDB=$1;
WORKDB=`echo ${WORKDB} | tr [A-Z] [a-z]`;
export PGHOST=/tmp

##------------------------------------------------------------------------------------------------------------
##-- 1. 데이터 명  ${WORKDB}
##------------------------------------------------------------------------------------------------------------
psql -q -c "CREATE DATABASE ${WORKDB} ENCODING = 'UTF8' template = template0 LC_COLLATE = 'C' LC_CTYPE = 'C'"
if [ $? -ne 0 ]; then
      date
      echo "===>[1][Already exists ${WORKDB} database]"
else
      date
      echo "[1][CREATE DATABASE Success!]"
fi
