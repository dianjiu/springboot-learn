package co.dianjiu.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSecurityApplication {

    public static void main(String[] args) {
        System.setProperty("cfg.env","local");
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }

}
