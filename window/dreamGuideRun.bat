@echo off
rem henc-dream-web-sample-0.4.2.war 실행하는 배치 파일
echo.
echo henc dreamframework Guide is starting......
echo.
echo http://localhost:9997
echo.
PAUSE
java -Dserver.port=9997 -jar henc-dream-web-sample-0.4.2.war
