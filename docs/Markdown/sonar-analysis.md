## Static Analysis and Code Coverage with sonar

1. Move to project location

`cd /opt/sonarqube/bin/linux-x86-64/`

2. Run sonar

`./sonar.sh start`

3. Move to project location

`cd ~/Development/repos/java/master-data`

4. Run unit tests

`mvn clean test`

5. Send code and reports to Sonar

`mvn sonar:sonar`

6. Access 

`http://localhost:9000`



