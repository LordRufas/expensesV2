@echo off

start cmd /k "npm --prefix .\frontend\expensesv2 start"

SET "JAVA_HOME=%CD%\Maven\jdk-25"
SET "PATH=%JAVA_HOME%\bin;%PATH%"

java -version 
cd backend\expensesV2\
java -jar target\expensesV2-0.0.1-SNAPSHOT.jar

pause