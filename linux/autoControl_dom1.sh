#!/bin/bash

#echo $#
if [ $# -lt 2 ]
then
	echo "2 paramters needed"
	exit -1
fi

echo "***** START ("$(date +'%Y%m%d %T')") *****"

sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/BOOT_MNP_LIST" "EO_MNP1"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/BOOT_MONI_LIST" "EO_MNP1"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/BOOT_PPA_LIST" "EO_MNP1"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/EO_GRP1_UMC_HTM_EP_START.list" "EO_GRP1"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/EO_NAC1_SKT_LGU_EP_START.list" "EO_NAC1"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/ONLINE_TCP_EP_START_EO_FNC1.list" "EO_FNC1"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/ONLINE_TCP_EP_START_EO_GRP1.list" "EO_GRP1"
sleep 1
sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/ONLINE_TCP_EP_START_EO_NAC1.list" "EO_NAC1"
sleep 1

if [ $2 = "1" ]
then
	echo "[ep $1 completed]"
elif [ $2 = "2" ]
then
	echo "[endpoint $1 completed]"
	sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/SCHEDULE_START_EO_FNC1.list" "EO_FNC1"
	sleep 1
	sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/SCHEDULE_START_EO_GRP1.list" "EO_GRP1"
	sleep 1
	sh /app/infinilink/properties/rolling_deploy/epControl.sh $1 "/GW_REPO/ep_control_list/SCHEDULE_START_EO_NAC1.list" "EO_NAC1"
	sleep 1
	echo "[scheduler $1 completed]"
else
	echo "improper parameter"
fi

echo "***** END ("$(date +'%Y%m%d %T')") *****"
