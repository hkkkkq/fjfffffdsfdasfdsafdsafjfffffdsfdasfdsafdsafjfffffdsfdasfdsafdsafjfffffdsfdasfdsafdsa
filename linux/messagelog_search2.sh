#!/bin/bash

# sh /home/infadm/monitoring_chk_sh/messagelog_search2.sh io_cdrm1_message_2016082214.log herasoo
# ./messagelog_search2.sh /applog/infinilink/message_log/io_cdrm1/io_cdrm1_message_2016082214.log herasoo

FILENAME=$1
SEARCH_TEXT=$2

sed -e ':a;N;$!ba;s/\n//g' -e 's/\[\#/\r\n\[\#/g' ${FILENAME} | grep -a ${SEARCH_TEXT}