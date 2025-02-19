package vn.mos.core.sercurities.properties;

import org.springframework.stereotype.Component;

import java.util.List;

@Component  // ✅ Spring sẽ đăng ký Bean này
public class PublicRoutesProviderImpl implements PublicRoutesProvider {

    @Override
    public List<String> getPublicRoutes() {
        return List.of(
            "/v1/public/**",
            "/swagger-ui/**"
        );
    }
}
