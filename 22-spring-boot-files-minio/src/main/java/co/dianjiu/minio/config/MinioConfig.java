package co.dianjiu.minio.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio 核心配置类
 */
@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinioConfig {

    @Autowired
    private MinioProp minioProp;

    /**
     * 获取 MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioProp.getEndpoint())
                        .credentials(minioProp.getAccesskey(), minioProp.getSecretKey())
                        .build();
        return minioClient;
    }
}

