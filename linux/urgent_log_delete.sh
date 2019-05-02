#/bin/bash

#delete files except today and yesterday logs 

TODAY=$(date +%Y%m%d)
YESTERDAY=$(date -d "-1 day" +%Y%m%d)

find /applog/infinilink/rte_log -type f -and -name "*rte_*.log" -and ! -name "*rte_${TODAY}*.log" -and ! -name "*rte_${YESTERDAY}*.log" | xargs rm -rf
find /applog/infinilink/message_log -type f -and -name "*message_*.log" -and ! -name "*message_${TODAY}*.log" -and ! -name "*message_${YESTERDAY}*.log" | xargs rm -rf