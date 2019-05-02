# GC Uage
# gc.sh i1

if [ "$1" = "i1" ]; then
   INS="eo_cdrm1_svr1"
fi

if [ "$1" = "i2" ]; then
   INS="cdmDom01Svr12"
fi



echo "INSTANCE NAME : ${INS}"

PID=`jps -v | grep ${INS} | awk '{print $1}'`

/home/infadm/jdk1.7.0_60/bin/jstat -gc -h20 -t ${PID} 3000 10000
