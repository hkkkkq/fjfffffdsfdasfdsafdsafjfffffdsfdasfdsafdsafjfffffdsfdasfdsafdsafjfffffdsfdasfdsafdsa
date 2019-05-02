#!/bin/bash

#Delete log data passed over 40 days

#### Tibero RDBMS Env ####
export TB_HOME=/tibero/tibero5
export TB_SID=SITGWE01
export TB_PROF_DIR=$TB_HOME/bin/prof
export PATH=$TB_HOME/bin:$TB_HOME/client/bin:$PATH
export LD_LIBRARY_PATH=$TB_HOME/lib:$TB_HOME/client/lib:$LD_LIBRARY_PATH


DAY_TOMORROW=$(date -d "1 day" +%d)
FORTY_DAYS_BEFORE=$(date -d "-40 day" +%Y/%m/%d)
_HOSTNAME=`hostname`
_DATE=`date +'%Y%m%d'`


echo "*** day of tomorrow: " $DAY_TOMORROW >> $_HOSTNAME\_$_DATE.log 
echo "*** 40 days ago: " $FORTY_DAYS_BEFORE >> $_HOSTNAME\_$_DATE.log 
echo "*** db scrips are as below" >> $_HOSTNAME\_$_DATE.log 
echo "*** 1) delete from il_trace_log_"$DAY_TOMORROW" where datetime < to_date('${FORTY_DAYS_BEFORE}','YYYY/MM/DD'); commit;" >> $_HOSTNAME\_$_DATE.log 
echo "*** 2) delete from il_transaction_log_"$DAY_TOMORROW" where starttime < to_date('${FORTY_DAYS_BEFORE}','YYYY/MM/DD'); commit;" >> $_HOSTNAME\_$_DATE.log 
echo "*** 3) delete from infl_custom_log_txn where reg_dt < to_date('${FORTY_DAYS_BEFORE}','YYYY/MM/DD'); commit;" >> $_HOSTNAME\_$_DATE.log 

echo " " >> $_HOSTNAME\_$_DATE.log
echo " " >> $_HOSTNAME\_$_DATE.log
echo "Result..." >> $_HOSTNAME\_$_DATE.log


tbsql -s infinilink/infinilink <<EOF >> $_HOSTNAME\_$_DATE.log 
        delete from il_trace_log_${DAY_TOMORROW} where datetime < to_date('${FORTY_DAYS_BEFORE}','YYYY/MM/DD');
        commit;
        delete from il_transaction_log_${DAY_TOMORROW} where starttime < to_date('${FORTY_DAYS_BEFORE}','YYYY/MM/DD');
        commit;
        delete from infl_custom_log_txn where reg_dt < to_date('${FORTY_DAYS_BEFORE}','YYYY/MM/DD');
        commit;
exit;
EOF