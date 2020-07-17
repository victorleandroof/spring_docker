ANHEUSER-BUSCH INBEV: CODE CHALLENGE
---
Prerequisite
---
* Maven 3
* Java 8
* Docker 1.13.0+
---
Preparing environment
---
```
cd oatuh-app
mvn clean package dockerfile:build 
cd ..
cd product-app
mvn clean package dockerfile:build 
```
---
Running with Docker Compose
---
```
docker-compose up
```

