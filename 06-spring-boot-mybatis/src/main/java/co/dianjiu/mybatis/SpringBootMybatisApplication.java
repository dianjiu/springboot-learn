package co.dianjiu.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
        System.out.println("请访问 http://localhost:8080/demo/restart");
        System.out.println("请访问 http://localhost:8080/demo/reset");
        System.out.println("请访问 http://localhost:8080/demo/userList");
        System.out.println("请访问 http://localhost:8080/druid/login.html");
    }

}
