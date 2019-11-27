## Setup www server

1. Install nginx server

`sudo apt-get install nginx`

2. Modify configuration file

`sudo nano /etc/nginx/sites-available/default`

Edit lines:

`server {`

`listen 80 default_server;`

`listen [::]:80 default_server;`

`return 301 https://$host$request_uri;`

`}`

`server {`

`        listen 443 ssl;`

`        #listen [::]:443 ssl;`

`        ssl on;`

`        ssl_certificate /etc/letsencrypt/live/adamkorzeniak.pl/fullchain.pem;`

`        ssl_certificate_key /etc/letsencrypt/live/adamkorzeniak.pl/priv`key.pem;`

`        root /var/www/html;`

`		 index index.html index.htm;`

`		 server_name adamkorzeniak.pl www.adamkorzeniak.pl;`

`        location / {`

`                try_files $uri $uri/ =404;`

`        }`

`}`

4. Restart nginx

`sudo systemctl restart nginx.service`

