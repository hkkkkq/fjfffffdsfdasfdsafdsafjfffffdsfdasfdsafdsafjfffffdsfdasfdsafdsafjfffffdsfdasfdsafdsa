#!/bin/sh


if [ $# -ne 2 ]; then
    echo "Usage: $0 DATABASE_NAME OWNER"
    exit;
fi;

./01_kt_createdb.sh $1
./02_kt_createschema.sh $1 $2
