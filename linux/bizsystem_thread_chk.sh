#!/bin/bash

# ./bizsystem_thread_chk eo_cdrm1_svr2

SERVERNAME=$1
BIZSYSYEM=${SERVERNAME%_*}


while true
do
sleep 1
YMD=$(date +"%Y%m%d%H");
echo "======== bizststem thread check ========"
tail -1000 /applog/infinilink/rte_log/$BIZSYSYEM/${SERVERNAME}_rte_$YMD.log | grep -a 'active threads ='  | sed -e 's/|INFO.*Running//g' | sed -e 's/, prioritized.*//g' | sed -n 1p
echo ""
done