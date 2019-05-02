#!/bin/bash

function ipchk()
{

local a=$(netstat -an | grep "$1" | grep ESTABLISHED | wc -l);

if [ "$a" -gt 0 ]
then
        echo "$1 is ESTABLISHED($a)"
        return 0
else
        echo "$1 is NOT ESTABLISHED!!!!!!!!!!!!!"
        return 1
fi

}

while true;

echo ""
echo ""
echo "---------------------------- 대외 PRD TCP 연결유지 포트 점검 시작 ----------------------------"
echo "EKFT_TCP_IN_01_EP_001  : " `ipchk 10.220.212.164:17714`
echo "LNOM_TCP_OUT_01_EP_002 : " `ipchk 210.123.91.23:17714`
echo "---------------------------- 대외 PRD TCP 연결유지 포트 점검 끝 ----------------------------"

do sleep 5;
done
