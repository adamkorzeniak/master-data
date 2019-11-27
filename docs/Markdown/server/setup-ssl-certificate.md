## SSL Certificate

1. Clone certbot repository

`git clone https://github.com/certbot/certbot.git /home/pi/server/tools/certbot`

2. Stop nginx server

`sudo systemctl stop nginx.service`

3. Create certificate

`./certbot-auto certonly -a standalone -d adamkorzeniak.pl -d www.adamkorzeniak.pl -d api.adamkorzeniak.pl`

4. Start nginx server

`sudo systemctl restart nginx.service`

5. Convert format

`sudo openssl pkcs12 -export -in /etc/letsencrypt/live/adamkorzeniak.pl/fullchain.pem -inkey /etc/letsencrypt/live/adamkorzeniak.pl/privkey.pem -out /home/pi/server/certs/keystore.p12 -name tomcat -CAfile /etc/letsencrypt/live/adamkorzeniak.pl/chain.pem -caname root`

6. Allow read access for keystore file

`sudo chmod 744 /home/pi/server/certs/keystore.p12`

3.1. In case of errors at point 3

`sudo nano /etc/pip.conf`

comment `pipwheals` line
