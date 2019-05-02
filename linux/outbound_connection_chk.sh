#!/bin/bash

function testing(){

	echo -n "** $1) connection test[$4] $2:$3 ==> ";
	nc -z $2 $3 1>/dev/null;
	if [ $? != 0 ]; then
		echo "FAIL"
	else
		echo "SUCCESS"
	fi

}

echo ""
echo "======================= Start: " `date +'%Y/%m/%d %T'` " ======================="

# add OUTbound Target Server info
testing 1 10.217.136.62 38088 comment1
testing 2 10.217.136.62 38068 comment2

echo "================================== END ====================================="
echo ""
