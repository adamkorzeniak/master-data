#!/bin/bash
cd /home/adam/Development/repos/java/master-data
echo Moved to project directory
echo Run Build
./mvnw clean package -DskipTests
echo copy application to server
scp target/master-data*.jar pi@192.168.0.87:/home/pi/server/app/master-data.jar

echo raspberry pi
ssh pi@192.168.0.87

echo copy to location
sudo cp /home/pi/server/app/master-data.jar /var/my-apps/master-data/
