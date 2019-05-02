#!/bin/sh

#written by Wonho Jeon

#directory location
LOC=$1

#search condition ex) server.log.2018-04-16
#"server.log."
PREFIX=$2
#""
SUFFIX=$3

#date information index in filename by "." ex) server.log.2018-04-16 => 3
DATE_INDEX=$4

#date delimiter ex) 2018-04-16 => "-", 20180416 => ""
DATE_DELIMITER=$5

# only print target file list(y)
ONLY_LIST_FILE=$6

BASE_DATE=$(date -d "-10 day" +%Y%m%d)
#echo $BASE_DATE

cd $LOC

LIST=`ls -Al ${PREFIX}*${SUFFIX} | awk '{print $NF}'`

for i in $LIST
do
	#echo $i
	if [ -f $i ]; then
		if [[ ! $i =~ \.gz$ ]]; then
			#server.log.2018-04-16
			#echo $i
			FILE_DATE=$(echo $i | awk -F '.' '{print $'${DATE_INDEX}'}')
			#2018-04-16
			#echo $FILE_DATE
			FILE_DATE=${FILE_DATE//$DATE_DELIMITER/""}
			#20180416
			#echo $FILE_DATE
			FILE_DATE=${FILE_DATE:0:8}
			#20180416
			#echo $FILE_DATE
			if [ $FILE_DATE -le $BASE_DATE ]; then
			
				if [ $ONLY_LIST_FILE == "y" ]; then
					echo $i
				else
					gzip $i
				fi
			fi
		fi
	fi
done
