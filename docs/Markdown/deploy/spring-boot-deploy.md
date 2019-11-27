## Deploy Spring Boot application

1. Move to project location

`cd /home/adam/Development/repos/java/master-data`

2. Run build

`./mvnw clean package`

3. Copy application to server

`scp target/master-data*.jar pi@192.168.0.xxx:/home/pi/server/app/master-data.jar`

`scp target/master-data*.jar pi@192.168.0.xxx:/home/pi/server/app/master-data-stage.jar`

4. SSH into server

`ssh pi@192.168.0.xxx`

5. Copy file to service directory

`sudo cp /home/pi/server/app/master-data.jar /var/my-apps/master-data/`

`sudo cp /home/pi/server/app/master-data-stage.jar /var/my-apps/master-data/`

6. Restart service

`sudo systemctl restart master-data`

`sudo systemctl restart master-data-stage`





