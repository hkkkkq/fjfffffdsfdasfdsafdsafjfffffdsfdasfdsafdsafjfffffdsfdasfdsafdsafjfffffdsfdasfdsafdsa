#!/bin/bash

# ./bizsystem_thread_chk_for_mon eo_cdrm1_svr2

SERVERNAME=$1
BIZSYSYEM=${SERVERNAME%_*}

YMD=$(date +"%Y%m%d%H");
COUNT=$(tail -1000 /applog/infinilink/rte_log/$BIZSYSYEM/${SERVERNAME}_rte_$YMD.log | grep -a 'active threads ='  | sed -e 's/\[#|.*, active threads = //g' | sed -e 's/, queued tasks.*//g' | sed -n 1p)

if [ -z $COUNT ]
then
	echo 0 
else	
	echo $COUNT
fi
