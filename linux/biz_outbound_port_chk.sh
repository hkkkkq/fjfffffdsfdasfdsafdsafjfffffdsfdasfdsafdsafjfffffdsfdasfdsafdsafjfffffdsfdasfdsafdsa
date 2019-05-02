#!/bin/bash

function ipchk()
{

local a=$(netstat -an | grep "$1" | grep "ESTABLISHED" | wc -l);

echo "$2 :  $1 is running($a)"
return 0
}

while true;

echo ""
echo ""
echo "---------------------------- 대내 PRD OUTbound포트 ESTABLISHED 점검 시작 ----------------------------"
echo " LNMM_TCP_OUT_001_PORT (10.220.175.23:9001)" `ipchk 10.220.175.23:9001`
echo " LREC_TCP_OUT_001_PORT (10.213.224.224:33000)" `ipchk 10.213.224.224:33000`
echo " NCDM_WS_OUT_001_PORT (10.219.3.190:5000)" `ipchk 10.219.3.190:5000`
echo " NCRC_WS_OUT_001_PORT (10.219.3.110:5000)" `ipchk 10.219.3.110 5000`
echo "---------------------------- 대내 PRD OUTbound포트 ESTABLISHED 점검 끝 ----------------------------"

do sleep 10;
done
