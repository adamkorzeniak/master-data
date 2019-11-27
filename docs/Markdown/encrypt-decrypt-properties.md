## Encryption of properties with Jasypt

#### Setup Spring Boot Application

1. Add Jasypt dependency to POM

`<dependency>`

`    <groupId>com.github.ulisesbocchio</groupId>`

`    <artifactId>jasypt-spring-boot-starter</artifactId>`

`    <version>2.0.0</version>`

`</dependency>`

2. Add Annotation to Application class

`@EnableEncryptableProperties`

3. Put encrypted property in property file

`encryptedProperty: ENC(23ClLWiedLx8v6XT6Wk+Bg==)`

4. Pass encryption key to application

RMB on Project => Run As => Run Configurations

Choose Spring Boot App configuration and choose ‘Arguments’ Tab

Add following option to VM Arguments:

`-Djasypt.encryptor.password=ENCRYPTION_KEY`

#### Encrypt and decrypt properties

1. Move to project location:

`cd /home/adam/Development/repos/java/master-data`

2. Encrypt property

`./tools/jasypt/bin/encrypt.sh input=PROPERTY_VALUE password=ENCRYPTION_KEY`

3. Put encrypted property in property file

`encryptedProperty: ENC(23ClLWiedLx8v6XT6Wk+Bg==)`

4. Decrypt property

`./tools/jasypt/bin/decrypt.sh input=ENCRYPTED_PROPERTY_VALUE password=ENCRYPTION_KEY`



