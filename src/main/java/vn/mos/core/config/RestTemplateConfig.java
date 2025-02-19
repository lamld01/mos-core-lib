package vn.mos.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import vn.mos.core.properties.RestTemplateProperties;

import java.time.Duration;

/**
 * Cấu hình RestTemplate với timeout lấy từ application.yml
 */
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateProperties properties; // Inject properties

    /**
     * Cấu hình bean RestTemplate với thời gian timeout.
     *
     * @param restTemplateBuilder Đối tượng builder hỗ trợ cấu hình RestTemplate.
     * @return RestTemplate đã cấu hình sẵn timeout.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
            .connectTimeout(Duration.ofMillis(properties.getConnectTimeout()))
            .readTimeout(Duration.ofMillis(properties.getReadTimeout()))
            .build();
    }
}
