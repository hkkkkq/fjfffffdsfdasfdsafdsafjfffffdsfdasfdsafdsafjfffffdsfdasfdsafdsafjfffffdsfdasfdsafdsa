#!/bin/bash

#Delete log data passed over 20 days

#### Tibero RDBMS Env ####
export TB_HOME=/tibero/tibero5
export TB_SID=SITGWE01
export TB_PROF_DIR=$TB_HOME/bin/prof
export PATH=$TB_HOME/bin:$TB_HOME/client/bin:$PATH
export LD_LIBRARY_PATH=$TB_HOME/lib:$TB_HOME/client/lib:$LD_LIBRARY_PATH


DAY_PLUS_1=$(date -d "1 day" +%d)
DAY_PLUS_2=$(date -d "2 day" +%d)
DAY_PLUS_3=$(date -d "3 day" +%d)
DAY_PLUS_4=$(date -d "4 day" +%d)
DAY_PLUS_5=$(date -d "5 day" +%d)
DAY_PLUS_6=$(date -d "6 day" +%d)
DAY_PLUS_7=$(date -d "7 day" +%d)
DAY_PLUS_8=$(date -d "8 day" +%d)
DAY_PLUS_9=$(date -d "9 day" +%d)
DAY_PLUS_10=$(date -d "10 day" +%d)



TWENTY_DAYS_BEFORE=$(date -d "-20 day" +%Y/%m/%d)
_HOSTNAME=`hostname`
_DATE=`date +'%Y%m%d'`


echo "*** 20 days ago: " $TWENTY_DAYS_BEFORE >> $_HOSTNAME\_$_DATE.log 
echo "*** db scrips are as below" >> $_HOSTNAME\_$_DATE.log 
echo "*** 1) truncate table il_trace_log_"$DAY_PLUS_1";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_2";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_3";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_4";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_5";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_6";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_7";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_8";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_9";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_trace_log_"$DAY_PLUS_10";" >> $_HOSTNAME\_$_DATE.log
echo "*** 2) truncate table il_transaction_log_"$DAY_PLUS_1";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_transaction_log_"$DAY_PLUS_2";" >> $_HOSTNAME\_$_DATE.log
echo "***    truncate table il_transaction_log_"$DAY_PLUS_3";" >> $_HOSTNAME\_$_DATE.log 
echo "***    truncate table il_transaction_log_"$DAY_PLUS_4";" >> $_HOSTNAME\_$_DATE.log 
echo "***    truncate table il_transaction_log_"$DAY_PLUS_5";" >> $_HOSTNAME\_$_DATE.log 
echo "***    truncate table il_transaction_log_"$DAY_PLUS_6";" >> $_HOSTNAME\_$_DATE.log 
echo "***    truncate table il_transaction_log_"$DAY_PLUS_7";" >> $_HOSTNAME\_$_DATE.log 
echo "***    truncate table il_transaction_log_"$DAY_PLUS_8";" >> $_HOSTNAME\_$_DATE.log 
echo "***    truncate table il_transaction_log_"$DAY_PLUS_9";" >> $_HOSTNAME\_$_DATE.log 
echo "***    truncate table il_transaction_log_"$DAY_PLUS_10";" >> $_HOSTNAME\_$_DATE.log 
echo "*** 3) delete from infl_custom_log_txn where reg_dt < to_date('${TWENTY_DAYS_BEFORE}','YYYY/MM/DD'); commit;" >> $_HOSTNAME\_$_DATE.log 

echo " " >> $_HOSTNAME\_$_DATE.log
echo " " >> $_HOSTNAME\_$_DATE.log
echo "Result..." >> $_HOSTNAME\_$_DATE.log


tbsql -s infinilink/infinilink <<EOF >> $_HOSTNAME\_$_DATE.log 
        truncate table il_trace_log_${DAY_PLUS_1};
        truncate table il_trace_log_${DAY_PLUS_2};
        truncate table il_trace_log_${DAY_PLUS_3};
        truncate table il_trace_log_${DAY_PLUS_4};
        truncate table il_trace_log_${DAY_PLUS_5};
        truncate table il_trace_log_${DAY_PLUS_6};
        truncate table il_trace_log_${DAY_PLUS_7};
        truncate table il_trace_log_${DAY_PLUS_8};
        truncate table il_trace_log_${DAY_PLUS_9};
        truncate table il_trace_log_${DAY_PLUS_10};
        truncate table il_transaction_log_${DAY_PLUS_1};
        truncate table il_transaction_log_${DAY_PLUS_2};
        truncate table il_transaction_log_${DAY_PLUS_3};
        truncate table il_transaction_log_${DAY_PLUS_4};
        truncate table il_transaction_log_${DAY_PLUS_5};
        truncate table il_transaction_log_${DAY_PLUS_6};
        truncate table il_transaction_log_${DAY_PLUS_7};
        truncate table il_transaction_log_${DAY_PLUS_8};
        truncate table il_transaction_log_${DAY_PLUS_9};
        truncate table il_transaction_log_${DAY_PLUS_10};
        delete from infl_custom_log_txn where reg_dt < to_date('${TWENTY_DAYS_BEFORE}','YYYY/MM/DD');
        commit;
exit;
EOF