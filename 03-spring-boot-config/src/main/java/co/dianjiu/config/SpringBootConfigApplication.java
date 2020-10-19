package co.dianjiu.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootConfigApplication {

    public static void main(String[] args) {
        //System.setProperty("cfg.env","local");
        SpringApplication.run(SpringBootConfigApplication.class, args);
    }

}
