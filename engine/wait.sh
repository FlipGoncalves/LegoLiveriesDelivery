#!/bin/bash

curl mysqldbengine:3306
RESP_STATUS=$?
RESP_52=52

while [[ $RESP_STATUS -eq 7 ]]; do
  sleep 1
  curl mysqldbengine:3306
  RESP_STATUS=$?
  echo $RESP_STATUS
done

java -cp app:app/lib/* tqs.project.ProjectApplication