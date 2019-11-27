## Deploy website

1. Copy website to server

`scp -r /home/adam/Development/repos/web/personal-website/* pi@192.168.0.xxx:/home/pi/server/www`

2. SSH into server

`ssh pi@192.168.0.xxx`

3. Move website to www location

`sudo rm -rf /var/www/html/*`

`sudo mv /home/pi/server/www/* /var/www/html`

4. Restart nginx

`sudo systemctl restart nginx.service`
