#!/bin/bash

# ./compress_rte_log.sh eo_cdrm1_svr2 20160213

SERVERNAME=$1
BIZSYSYEM=${SERVERNAME%_*}
DATE=$2
TODAY=$(date +%Y%m%d)

echo "============= RTE LOG gzip COMPRESSION ============="
echo "Param1(Servername): " $SERVERNAME
echo "Param2(Date): " $DATE

if [ "$DATE" -ne "$TODAY" -a ${#DATE} -eq 8 ] 
then
	COUNT=$(find /applog/infinilink/rte_log/$BIZSYSYEM/ -type f -name "${SERVERNAME}_rte_$DATE*.log" | wc -l)
	if [ $COUNT -gt 0 ]
	then
		ls /applog/infinilink/rte_log/$BIZSYSYEM/${SERVERNAME}_rte_$DATE*.log | xargs gzip
		echo "Result: Success"
		exit 0
	else
		echo "Result: Fail(There are no files)"
		exit -1
	fi
else
	echo "Result: Fail(Cannot compress today's logs or wrong Param2 Date length)"
	exit -2
fi