#/bin/bash

_TODAY=$(date +%Y%m%d)
_1DAYSAGO=$(date -d "-1 day" +%Y%m%d)
_2DAYSAGO=$(date -d "-2 day" +%Y%m%d)
_3DAYSAGO=$(date -d "-3 day" +%Y%m%d)
_4DAYSAGO=$(date -d "-4 day" +%Y%m%d)
_5DAYSAGO=$(date -d "-5 day" +%Y%m%d)
_6DAYSAGO=$(date -d "-6 day" +%Y%m%d)
_7DAYSAGO=$(date -d "-7 day" +%Y%m%d)


find /applog/infinilink/jeus_log -type f -name "JeusServer_*.log" -and ! -name "JeusServer_${_TODAY}.log" -and ! -name "JeusServer_${_1DAYSAGO}.log" -and ! -name "JeusServer_${_2DAYSAGO}.log" -and ! -name "JeusServer_${_3DAYSAGO}.log" -and ! -name "JeusServer_${_4DAYSAGO}.log" -and ! -name "JeusServer_${_5DAYSAGO}.log" -and ! -name "JeusServer_${_6DAYSAGO}.log" -and ! -name "JeusServer_${_7DAYSAGO}.log" | xargs rm -rf
find /applog/infinilink/jeus_log -type f -name "access_*.log*" -and ! -name "access_${_TODAY}.log*" -and ! -name "access_${_1DAYSAGO}.log*" -and ! -name "access_${_2DAYSAGO}.log*" -and ! -name "access_${_3DAYSAGO}.log*" -and ! -name "access_${_4DAYSAGO}.log*" -and ! -name "access_${_5DAYSAGO}.log*" -and ! -name "access_${_6DAYSAGO}.log*" -and ! -name "access_${_7DAYSAGO}.log*" | xargs rm -rf
