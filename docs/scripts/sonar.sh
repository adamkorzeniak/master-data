#!/bin/bash
cd /home/adam/Development/repos/java/master-data
echo Moved to project directory
echo Stopping Docker
sudo docker system prune
echo Docker Stopped
echo Starting Docker
sudo docker run -d --name sonarqube -p 9000:9000 sonarqube &
echo Starting Unit Tests
./mvnw clean test
echo Sending code to Sonar
./mvnw sonar:sonar
echo Done: Access http://localhost:9000


