#!/bin/bash

P_80_443=$(ps -ef | grep httpd | grep -v grep | awk '{print $(NF-2)}' | grep apache | wc -l)
T_80_443=$(ps -efL | grep httpd | grep -v grep | awk '{print $(NF-2)}' | grep apache | wc -l)
P_7000_7443=$(ps -ef | grep httpd | grep -v grep | grep 7000_7443 | wc -l)
T_7000_7443=$(ps -efL | grep httpd | grep -v grep | grep 7000_7443 | wc -l)

echo "===============" $(date +'%Y-%m-%d %T') "================"
echo "80_433 Port PROCESS cnt: "$P_80_443
echo "80_433 Port THREAD cnt: "$T_80_443
echo "7000_7433 Port PROCESS cnt: "$P_7000_7443
echo "7000_7433 Port THREAD cnt: "$T_7000_7443
echo "========================================================="
echo ""
