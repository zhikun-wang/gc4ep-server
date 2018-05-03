#!/bin/bash

pid=`ps -ef |grep bg-admin-web |  awk '{print $2}'`

if  [ ! -n "$pid" ] ;then
    echo "bg-admin-web is not running!"
else
    echo "bg-admin-web is running"
    echo $pid
    kill -9 $pid
fi

echo "starting bg-admin-web"
echo $@
mkdir -p logs
java -jar bg-admin-web-1.0-SNAPSHOT.jar  --server.port=18011 --spring.profiles.active=prod $@ > logs/km1.log &
echo "starting completed"
