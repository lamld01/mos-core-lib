package vn.mos.core.sercurities.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.mos.core.properties.SecurityProperties;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutesProviderImpl implements RoutesProvider {

    private final SecurityProperties securityProperties;

    @Override
    public List<String> getPublicRoutes() {
        return securityProperties.getPublicRoutes();
    }

    @Override
    public List<String> getCorsAllowUrl() {
        return securityProperties.getCorsAllowUrl();
    }
}
