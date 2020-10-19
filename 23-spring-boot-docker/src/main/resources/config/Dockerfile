# Docker image for springboot file run
# VERSION 0.0.1
# Author: eangulee
# 基础镜像使用java
# FROM frolvlad/alpine-oraclejdk8:slim
# 指明当前镜像继承的基镜像,编译当前镜像时候会自动下载基镜像
FROM java:11
# 当前镜像的作者和邮箱,使用空格隔开
MAINTAINER dianjiu <dianjiu@dianjiu.cc>
# VOLUME 挂载目录,指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 在当前镜像上执行Linux命令,这里我执行了2个run指令,第二个run指令是为了解决容器和宿主机时间不一致的问题
ADD spring-boot-docker-1.0-SNAPSHOT.jar app.jar
# 时区
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo 'Asia/Shanghai' >/etc/timezone

# 自定义配置开始
ENV SPRING-CLOUD-EUREKA-ZONE http://123.456.789.10:8761/eureka/
ENV SPRING-CLOUD-MYSQL-HOST 123.456.789.10
ENV SPRING-CLOUD-DB-NAME edu
ENV SPRING-CLOUD-RABBIT-MQ-HOST 123.456.789.10
ENV SPRING-CLOUD-REDIS-HOST 123.456.789.10
# 自定义配置结束

# EXPOSE:监听的端口号
EXPOSE 8080

# 运行jar包
RUN bash -c 'touch /app.jar'
# 让容器像一个可执行程序一样运行
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]