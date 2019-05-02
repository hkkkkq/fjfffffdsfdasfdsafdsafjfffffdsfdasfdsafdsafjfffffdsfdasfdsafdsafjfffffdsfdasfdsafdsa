#!/bin/bash

# sh /home/infadm/monitoring_chk_sh/create_messagelog_by_ifid.sh io_cdrm1 BIZTXID NIFE_TEST1 -5 2

echo "* START( $(date +'%Y/%m/%d %T') ) *"

BIZSYSTEMID=$1
echo "*** BIZSYSTEMID: $BIZSYSTEMID ***"
BIZTXID=$2
echo "*** BIZTXID: $BIZTXID ***"
IFID=$3
echo "*** IFID: $IFID ***"
MINUSTIME=$4
echo "*** MINUSTIME: $MINUSTIME ***"
FLAG=$5
# FLAG=1 HOURLY
# FLAG=2 DAILY
echo "*** FLAG: $FLAG ***"

if [ "$FLAG" == "1" ]; then
	echo "***** HOURLY *****"
	DATE=$(date -d "${MINUSTIME} hour" +%Y%m%d%H)
	#echo $DATE
	SOURCE="/applog/infinilink/message_log/test/${BIZSYSTEMID}/${BIZSYSTEMID}_message_${DATE}.log"
	TARGET="/applog/infinilink/message_log/test/${BIZSYSTEMID}/${BIZSYSTEMID}_message_${DATE}_${IFID}_jwh.log"
	echo "***** SOURCE: $SOURCE *****"
  	echo "***** TARGET: $TARGET *****"
	if [ -f "$TARGET" ]; then
		rm -rf $TARGET	
	fi
	grep -aPzo "(?s)\[#\|(?:(?!\[#).)*?${BIZTXID}(?:(?!#\]).)*?\|#\]" ${SOURCE} > ${TARGET}
elif [ "$FLAG" == "2" ]; then
	echo "***** DAILY *****"
	DATE=$(date -d "${MINUSTIME} day" +%Y%m%d)
	#echo $DATE
	TIMES=("00" "01" "02" "03" "04" "05" "06" "07" "08" "09" "10" "11" "12" "13" "14" "15" "16" "17" "18" "19" "20" "21" "22" "23")
	TARGET="/applog/infinilink/message_log/test/${BIZSYSTEMID}/${BIZSYSTEMID}_message_${DATE}$_${IFID}_jwh.log"
	if [ -f "$TARGET" ]; then
                rm -rf $TARGET
  fi
	for i in "${TIMES[@]}";
	do
		SOURCE="/applog/infinilink/message_log/test/${BIZSYSTEMID}/${BIZSYSTEMID}_message_${DATE}${i}.log"
		echo "***** SOURCE: $SOURCE *****"
		grep -aPzo "(?s)\[#\|(?:(?!\[#).)*?${BIZTXID}(?:(?!#\]).)*?\|#\]" ${SOURCE} >> ${TARGET}
	done
else
	echo "***** WRONG FLAG PARAMETER *****"
fi

echo "* END( $(date +'%Y/%m/%d %T') ) *" 
