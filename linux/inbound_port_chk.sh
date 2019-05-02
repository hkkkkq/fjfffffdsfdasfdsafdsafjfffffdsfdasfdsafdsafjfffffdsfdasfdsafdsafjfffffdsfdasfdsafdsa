#!/bin/bash

function ipchk()
{

local a=$(netstat -an | grep "$1" | grep "ESTABLISHED\|LISTEN" | wc -l);

if [ "$a" -gt 0 ]
then
        echo "$1 is running"
        return 0
else
        echo "$1 is not running!!!"
        return 1
fi

}

echo "---------------------------- 대외 SIT Inbound포트 Listen 점검 시작 ----------------------------"
echo "EKAI_TCP_IN_001_PORT : " `ipchk 60017`
echo "EKAI_TCP_IN_002_PORT : " `ipchk 64060`
echo "EKCP_TCP_IN_001_PORT : " `ipchk 60011`
echo "EKFT_TCP_IN_001_PORT : " `ipchk 27714`
echo "EKIS_TCP_IN_001_PORT : " `ipchk 5233`
echo "ENIC_TCP_IN_001_PORT : " `ipchk 7016`
echo "LCIN_TCP_IN_001_PORT : " `ipchk 27721`
echo "LCIR_TCP_IN_001_PORT : " `ipchk 27723`
echo "LCIR_TCP_IN_002_PORT : " `ipchk 27724`
echo "LCNS_TCP_IN_001_PORT : " `ipchk 60016`
echo "EMHW_HTTP_IN_URL : " `ipchk 8070`
echo "WS_IN_PORT : " `ipchk 58088`
echo "---------------------------- 대외 SIT Inbound포트 Listen 점검 종료 ----------------------------"

