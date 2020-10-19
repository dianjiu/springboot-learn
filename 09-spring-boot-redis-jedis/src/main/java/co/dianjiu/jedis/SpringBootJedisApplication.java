package co.dianjiu.jedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJedisApplication {

    public static void main(String[] args) {
        System.setProperty("cfg.env","local");
        SpringApplication.run(SpringBootJedisApplication.class, args);
    }

}
