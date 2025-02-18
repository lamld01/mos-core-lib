package vn.mos.core.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "feign.client.config.default")
public class FeignClientConfig {

    private int connectTimeout;
    private int readTimeout;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("Content-Type", "application/json");
    }
}
