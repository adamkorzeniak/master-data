# Master Data

REST API providing an ability to manage personal data database (eg. watched movies, diet details).
Currently it supports only movies and errors related data.

You can check interactive API documentation below:
[API Docs](https://app.swaggerhub.com/apis-docs/adam-korzeniak/master-data/0.0.1)

#### Features
- Retrieve and manage movie data
- Retrieve and manage errors records (allow clients to register errors related to both server and client issues)

##### Movie functionality
- Search database for movies based on filters:
	- match: title
	- search: title, description, review, plot summary, genres
	- min/max: year, duration, rating, watch priority
	- exist: rating, watch priority, review, plot summary
	- order: title, year, duration, rating, watch priority
- Retrieve movie details for movie with given id
- Create movie in database
- Update movie in database
- Delete movie with given id
- Search database for movie genres based on filters:
	- match: name
	- search: name
	- exist: name
	- order: name
- Retrieve genre details for genre with given id
- Create genre in database
- Update genre in database
- Delete genre with given id
- Merge genres (movies with source genre change it to target genre, then source genre is being removed)

##### Error
- Search database for error records based on filters:
	- match: errorId, appId, name
	- search: errorId, appId, name, details, status, location
	- min/max: time
	- exist: details
	- order: errorId, appId, name, time
- Create error record in database
- Delete error record with given id

##### Diet functionality (in progress)
- 

## Deployed version

There are currently two environments deployed:

- Stage

https://api.adamkorzeniak.pl:8443/v0

Username: test

Password: test123

- Production

Both environments are deployed on Raspberry Pi using my private internet connection. In case of any internet or hardware issues servers might not be accessible. Please feel free to notify me via email or other way.

## API Docs

You can check and test REST Server functionality using interactive API documentation below:
[API Docs](https://app.swaggerhub.com/apis-docs/adam-korzeniak/master-data/0.0.1)

## Built With

* [Java 8](https://docs.oracle.com/javase/8/docs/) - Programming language
* [Spring Boot](https://spring.io/projects/spring-boot) - Main application framework
* [Hibernate](https://hibernate.org/) - Object-relational mapping framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [MySQL](https://www.mysql.com/) - Relational Database Management System
* [Lombok](https://projectlombok.org/) - Boilerplate Code Generator
* [Jasypt](http://www.jasypt.org/) - Properties encryption
* [SonarQube](https://www.sonarqube.org/) - Static code analysis
* [JaCoCo](https://www.eclemma.org/jacoco/) - Code coverage
