package vn.mos.core.config;

import org.springframework.stereotype.Component;
import vn.mos.core.properties.PublicRoutesProvider;

import java.util.List;

@Component  // ✅ Spring sẽ đăng ký Bean này
public class PublicRoutesProviderImpl implements PublicRoutesProvider {

    @Override
    public List<String> getPublicRoutes() {
        return List.of(
            "/v1/public/**",
            "/favicon.ico"
        );
    }
}
