package co.dianjiu.minio.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
     *
     * @return
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     */
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioProp.getEndpoint(), minioProp.getAccesskey(), minioProp.getSecretKey());
    }
}
