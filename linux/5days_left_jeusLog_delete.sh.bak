#/bin/bash

TODAY=$(date +%Y%m%d)
YESTERDAY=$(date -d "-1 day" +%Y%m%d)
TWODAYSAGO=$(date -d "-2 day" +%Y%m%d)
THREEDAYSAGO=$(date -d "-3 day" +%Y%m%d)

find /applog/infinilink/rte_log -type f -and -name "*rte_*.log" -and ! -name "*rte_${TODAY}*.log" -and ! -name "*rte_${YESTERDAY}*.log" -and ! -name "*rte_${TWODAYSAGO}*.log" -and ! -name "*rte_${THREEDAYSAGO}*.log" | xargs rm -rf
find /applog/infinilink/message_log -type f -and -name "*message_*.log" -and ! -name "*message_${TODAY}*.log" -and ! -name "*message_${YESTERDAY}*.log" -and ! -name "*message_${TWODAYSAGO}*.log" -and ! -name "*message_${THREEDAYSAGO}*.log" | xargs rm -rf
