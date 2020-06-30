package co.dianjiu.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRedisTemplateApplication {

    public static void main(String[] args) {
        System.setProperty("cfg.env","local");
        SpringApplication.run(SpringBootRedisTemplateApplication.class, args);
    }

}
