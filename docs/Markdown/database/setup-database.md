## Install and setup MySQL database

#### Install MySQL database

1. Install mysql database

`sudo apt install mariadb-server`

2. Login to database as a root

`sudo mysql`

3. Run queries from files to setup databases

`initialize-stage-database-queries.txt`

`initialize-prod-database-queries.txt`

4. Check users

`SELECT User FROM mysql.user`

5. Restart mysql

`systemctl restart mysql.service`

#### Create and import backups

1. Import backups

`mysql < backup.sql`

2. Export backups (either way)

`mysqldump -u root -p password authentication > authentication.sql`

`mysqldump –databases one two three > databases_backup.sql`

`mysqldump --all-databases > all_databases.sql`

#### Add user to users table

1. Access page

`https://www.browserling.com/tools/bcrypt`

2. Provide password with 10 rounds and encrypt it

3. Insert user to database

`INSERT INTO master_data.users (username, password, role)
VALUES (“test”, “PASSWORD”, “ROLE_ADMIN”);`
