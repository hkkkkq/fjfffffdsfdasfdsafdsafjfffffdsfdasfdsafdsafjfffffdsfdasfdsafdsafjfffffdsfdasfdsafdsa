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
echo "---------------------------- 대내 PRD Inbound포트 ESTABLISHED 점검 시작 ----------------------------"
echo "LIPI_TCP_IN_001_PORT" `ipchk 9002`
echo "LNMM_TCP_IN_001_PORT" `ipchk 9091`
echo "WS_IN_PORT" `ipchk 38088`
echo "---------------------------- 대내 PRD Inbound포트 ESTABLISHED 점검 끝 ----------------------------"

do sleep 10;
done

