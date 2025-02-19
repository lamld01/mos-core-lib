package vn.mos.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security") // ✅ Ánh xạ từ "security.public-routes"
public class SecurityProperties {
    private List<String> publicRoutes;
}
