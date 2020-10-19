package co.dianjiu.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.point9.hello.controller","cn.point9.common.*"})
public class SpringBootHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHelloApplication.class, args);
    }

}
