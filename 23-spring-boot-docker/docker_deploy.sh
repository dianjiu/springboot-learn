docker build -t spring-boot-docker-1.0-SNAPSHOT .
docker run -p 18899:8899 -t -dit --restart=always spring-boot-docker-1.0-SNAPSHOT