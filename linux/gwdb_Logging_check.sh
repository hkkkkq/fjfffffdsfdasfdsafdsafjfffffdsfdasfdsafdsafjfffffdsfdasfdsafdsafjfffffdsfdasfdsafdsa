#/bin/bash

#how to call: sh gwdb_Logging_check.sh gweap02

if [ $# -ne 1 ]
then
	echo "1 parameter is needed, it must be the hostname!!!"
	exit -1
fi

HOSTNAME=$1
TYPE=${HOSTNAME:2:1}
NUM=${HOSTNAME:5:2}

#echo $TYPE
#echo $NUM

if [ $TYPE == "i" ]
then
	if [ $NUM == "01" ]
	then
		SERVER=(io_cdrm1_svr1 ib_cdrm1_svr1 io_mms1_svr1 ib_bat1_svr1 io_grp1_svr1)
	elif [ $NUM == "02" ]
	then
		SERVER=(io_cdrm1_svr2 ib_cdrm1_svr2 io_mms1_svr2 ib_bat1_svr2 io_grp1_svr2)
	elif [ $NUM == "03" ]
        then
		SERVER=(io_cdrm1b_svr1 ib_cdrm1b_svr1 io_mms1b_svr1 ib_bat1b_svr1 io_grp1b_svr1)
  elif [ $NUM == "04" ]
  then
		SERVER=(io_cdrm1b_svr2 ib_cdrm1b_svr2 io_mms1b_svr2 ib_bat1b_svr2 io_grp1b_svr2)
	else
		echo "paramter is wrong!"
		exit -1
	fi
elif [ $TYPE == "e" ]
then
    if [ $NUM == "01" ]
    then
      SERVER=(eo_cdrm1_svr1 eb_cdrm1_svr1 eo_grp1_svr1 eo_mnp1_svr1 eo_fnc1_svr1 eo_fnp1_svr1 eb_bat1_svr1 eo_nac1_svr1)
    elif [ $NUM == "02" ]
    then
      SERVER=(eo_cdrm1b_svr1 eb_cdrm1b_svr1 eo_grp1b_svr1 eo_mnp1b_svr1 eo_fnc1b_svr1 eo_fnp1b_svr1 eb_bat1b_svr1 eo_nac1b_svr1)
    elif [ $NUM == "03" ]
    then
      SERVER=(eo_cdrm1_svr2 eb_cdrm1_svr2 eo_grp1_svr2 eo_mnp1_svr2 eo_fnc1_svr2 eo_fnp1_svr2 eb_bat1_svr2 eo_nac1_svr2)
    elif [ $NUM == "04" ]
    then
      SERVER=(eo_cdrm1b_svr2 eb_cdrm1b_svr2 eo_grp1b_svr2 eo_mnp1b_svr2 eo_fnc1b_svr2 eo_fnp1b_svr2 eb_bat1b_svr2 eo_nac1b_svr2)
    else
      echo "paramter is wrong!!"
      exit -1
    fi
else
	echo "paramter is wrong!!!"
	exit -1
fi

#echo $SERVER
#echo ${#SERVER}
#echo ${#SERVER[@]}

DATE=$(date +"%Y%m%d %T")
#echo $DATE

for svr in ${SERVER[@]}
do
	RESULT=$(tail -1000 /applog/infinilink/jeus_log/$svr/JeusServer.log | grep 'java.sql.BatchUpdateException' | wc -l)
	#echo $RESULT
	if [ $RESULT -gt 0 ]
	then
		echo "[$DATE] $svr DB Logging ERROR"
	else
		echo "[$DATE] $svr DB Logging OK"
	fi
	#sleep 1	
done