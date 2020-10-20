docker build -t spring-boot-docker-1.0-SNAPSHOT .
docker run -dit --net=host -p 18899:8899 -t --restart=always spring-boot-docker-1.0-SNAPSHOT

docker镜像与容器
    1,列出本地已有的所有镜像
    docker images
    REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
    ubuntu              14.04               90d5884b1ee0        5 days ago          188 MB
    php                 5.6                 f40e9e0f10c8        9 days ago          444.8 MB
    nginx               latest              6f8d099c3adc        12 days ago         182.7 MB
    2,拉取镜像
    docker pull <仓库地址>/<镜像名>:<镜像tag>
    docker push <IMAGE_ID>：上传image
    docker rmi <IMAGE_ID>：删除image
    3,启动容器
    docker run -it <镜像名>:<镜像tag> /bin/bash
    docker run -t -i ubuntu:14.04 /bin/bash
    -t:在新容器内指定一个伪终端或终端。
    -i:允许你对容器内的标准输入 (STDIN) 进行交互。
    该容器不以守护态运行，退出容器即关闭。
    4,后台守护进程启动
    docker run -dit <镜像名>:<镜像tag> /bin/bash
    docker run -dit  training/webapp python app.py
    -d即是以守护态运行
    5,自定义端口映射,启动服务
    宿主机到容器内的端口映射
    docker run -dit -p <hostPort>:<containerPort> --name <自定义容器名> <镜像名>:<镜像tag> /bin/bash && command1 command2
    docker run -dit -p 5000:5000 training/webapp python app.py
    -P：默认匹配docker容器的5000端口号到宿主机的49153 to 65535端口
    -p <HOT_PORT>:<CONTAINER_PORT>：指定端口号
    --name <自定义容器名>
    docker run -p 5000:5000：绑定特定端口号（主机的所有网络接口的5000端口均绑定容器的5000端口）
    docker run -p 127.0.0.1:5000:5000：绑定主机的特定接口的端口号  #访问本机的127.0.0.1:5000就能访问到容器的5000
    docker run -d -p 127.0.0.1:5000:5000/udp training/webapp python app.py：绑定udp端口号
    启动tomcat
    docker run -dit -p <hostPort>:<containerPort> --name <自定义容器名> <镜像名>:<镜像tag> /bin/bash && <tomcat启动命令>
    启动多个进程，使用&&连接多个启动命令即可
    6,查看容器进程
    docker ps   #查看运行的容器
    docker ps -a   #查看所有的容器
    7,快速查看映射的端口号
    docker port (ID或者名字)
    得知容器的某个确定端口映射到宿主机的端口号
    ubuntu@data3:~$ docker port bd6eecdd6b38
    80/tcp -> 0.0.0.0:8080
    8,查看日志
    docker logs [ID或者名字] 可以查看容器内部的标准输出
    docker logs -f 7a38a1ad55c6
    -f:让 dokcer logs 像使用 tail -f 一样来输出容器内部的标准输出。
    9,查看容器内的进程
    docker top [ID或者名字]   来查看容器内部运行的进程
    10,查看docker的一些底层信息
    docker inspect [ID或者名字]
    docker inspect determined_swanson
    -f：查找特定信息，如docker inspect -f '{{ .NetworkSettings.IPAddress }}' <container>
    11,停止，重启，移除
    docker stop [ID或者名字]
    docker start [ID或者名字]
    docker rm [ID或者名字]   #容器必须停掉
    docker rm `docker ps -a -q`：删除所有容器
    其他常用命令
    docker diff <CONTAINER_ID>：查看容器中的变化
    docker exec -it <CONTAINER> <COMMAND>：在容器里执行命令，并输出结果
    进入容器
    不要使用attach
    docker exec -it  <names> "/bin/bash"
    docker exec -it hungry_brown "/bin/bash"
Docker容器连接(掠过)现在主要是network了
    docker run -d -P --name <CONTAINER_NAME> --link <CONTAINER_NAME_TO_LINK>:<ALIAS>
数据管理
    -v 宿主机映射到容器内