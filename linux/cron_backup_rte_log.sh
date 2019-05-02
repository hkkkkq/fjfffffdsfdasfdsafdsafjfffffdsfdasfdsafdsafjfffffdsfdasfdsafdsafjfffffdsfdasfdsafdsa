#!/bin/bash

# ./cron_backup_rte_log.sh eo_cdrm1_svr1
#. /home/infadm/.bash_profile

SERVERNAME=$1
BIZSYSYEM=${SERVERNAME%_*}
TODAY=$(date +'%Y%m%d %T')
DATE=$(date -d '-4 day' +%Y%m%d)

echo "============= " $TODAY " CRON RTE_LOG BACK_UP ============="                                      >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
echo "Param1(Servername): " $SERVERNAME                                                                 >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
echo "TODAY: " $TODAY                                                                                   >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
echo "DATE: " $DATE                                                                                     >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
echo ""                                                                                                 >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
echo "1) gzip files moving"                                                                             >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
mv /applog/infinilink/rte_log/$BIZSYSYEM/*.log.gz /GW_REPO/rte_log_backup                               >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out 2>&1
echo ""                                                                                                 >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
echo "2) compressing"                                                                                   >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
find /applog/infinilink/rte_log/$BIZSYSYEM/ -type f -name ${SERVERNAME}_rte_$DATE*.log -exec gzip {} \; >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out 2>&1
echo "==========================================================="                                      >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out
echo ""                                                                                                 >> /applog/infinilink/rte_log/$BIZSYSYEM/cron_backup_rte_log_result.out