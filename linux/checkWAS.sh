#!/bin/bash

BMON=$(ps -ef | grep LogMonitoringAgent | grep -v grep | wc -l)
SAFEDB=$(ps -ef | grep initech | grep inisafe | grep -v grep | wc -l)
KKOS=$(ps -ef | grep kkosSvr11 | grep jvmRoute | grep -v grep | wc -l)
KKOS_CONN=$(netstat -an | grep 10.220.172.33:8019 | grep ESTABLISHED | wc -l)
KSHOP=$(ps -ef | grep kmosSvr12 | grep jvmRoute | grep -v grep | wc -l)
KSHOP_CONN=$(netstat -an | grep 10.220.172.33:8109 | grep ESTABLISHED | wc -l)
ZABBIX=$(ps -ef | grep zabbix | grep zabbix_agentd.conf | grep -v grep | wc -l)

echo "===============" $(date +'%Y-%m-%d %T') "================"

if [ $BMON == "1" ]; then
	echo "BMON is OK"
else
	echo "BMON is NOT OK"
fi

if [ $SAFEDB == "2" ]; then
        echo "SAFEDB is OK"
else
        echo "SAFEDB is NOT OK"
fi

if [ $KKOS == "1" ]; then
        echo "KKOS is OK"
else
        echo "KKOS is NOT OK"
fi

echo "KKOS_CONN : " $KKOS_CONN

if [ $KSHOP == "1" ]; then
        echo "KSHOP is OK"
else
        echo "KSHOP is NOT OK"
fi

echo "KSHOP_CONN : " $KSHOP_CONN

if [ $ZABBIX == "1" ]; then
        echo "ZABBIX is OK"
else
        echo "ZABBIX is NOT OK"
fi

echo "====================================================="
echo ""
