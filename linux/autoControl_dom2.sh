#!/bin/bash

#echo $#
if [ $# -lt 2 ]
then
	echo "2 paramters needed"
	exit -1
fi

echo "***** START ("$(date +'%Y%m%d %T')") *****"

sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/BOOT_FNP_ELIS_LIST" "EO_FNP1B"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/EB_BAT1B_BATCH_TCP_EP_START.list" "EB_BAT1B"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/ONLINE_TCP_EP_START_EO_FNC1B.list" "EO_FNC1B"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/ONLINE_TCP_EP_START_EO_GRP1B.list" "EO_GRP1B"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/ONLINE_TCP_EP_START_EO_NAC1B.list" "EO_NAC1B"
sleep 1

if [ $2 = "1" ]
then
	echo "[ep $1 completed]"
elif [ $2 = "2" ]
then
	echo "[endpoint $1 completed]"
	echo "[There are no schedulers in dom2]"
	echo "[scheduler $1 completed]"
else
	echo "improper parameter"
fi

echo "***** END ("$(date +'%Y%m%d %T')") *****"
