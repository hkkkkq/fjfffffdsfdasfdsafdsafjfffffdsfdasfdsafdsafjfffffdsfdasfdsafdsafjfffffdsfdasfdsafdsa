#!/bin/bash

# sh /home/infadm/monitoring_chk_sh/messagelog_search.sh io_cdrm1_message_2016082214.log herasoo
# sh ./messagelog_search.sh /applog/infinilink/message_log/io_cdrm1/io_cdrm1_message_2016082214.log herasoo

FILENAME=$1
SEARCH_TEXT=$2

grep -aPzo "(?s)\[#\|(?:(?!\[#).)*?${SEARCH_TEXT}(?:(?!#\]).)*?\|#\]" ${FnILENAME}