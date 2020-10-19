package co.dianjiu.log.config;

import co.dianjiu.log.interceptor.TraceIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public TraceIdInterceptor getTraceIdInterceptor() {
        return new TraceIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTraceIdInterceptor()).addPathPatterns("/*");
    }
}
