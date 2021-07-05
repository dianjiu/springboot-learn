package co.dianjiu.jedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories
@SpringBootApplication
public class SpringBootJedisApplication {

    public static void main(String[] args) {
        System.setProperty("cfg.env","local");
        SpringApplication.run(SpringBootJedisApplication.class, args);
    }

}
