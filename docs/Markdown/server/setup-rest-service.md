## Setup REST Service

1. Create file for services

`sudo touch /etc/systemd/system/master-data.service`

`sudo touch /etc/systemd/system/master-data-stage.service`

2. Edit file and put following content:

###### master-data.service

`[Unit]`

`Description=Master Data`

`After=syslog.target`


`[Service]`

`User=pi`

`ExecStart=/usr/bin/java -Denv=prod -Djasypt.encryptor.password=ENCRYPTIONKEY -jar /var/my-apps/master-data/master-data.jar`

`SuccessExitStatus=143`

`[Install]`

`WantedBy=multi-user.target`

###### master-data-stage.service

`[Unit]`

`Description=Master Data`

`After=syslog.target`

`[Service]`

`User=pi`

`ExecStart=/usr/bin/java -Denv=stage -Djasypt.encryptor.password=ENCRYPTIONKEY -jar /var/my-apps/master-data/master-data-stage.jar`

`SuccessExitStatus=143`

`[Install]`

`WantedBy=multi-user.target`

3. Add service to autostart

`sudo systemctl enable master-data.service`

`sudo systemctl enable master-data-stage.service`

4. Start service

`sudo systemctl start master-data.service`

`sudo systemctl start master-data-stage.service`

