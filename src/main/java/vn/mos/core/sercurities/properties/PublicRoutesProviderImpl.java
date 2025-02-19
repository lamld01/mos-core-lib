package vn.mos.core.sercurities.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.mos.core.properties.SecurityProperties;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PublicRoutesProviderImpl implements PublicRoutesProvider {

    private final SecurityProperties securityProperties;

    @Override
    public List<String> getPublicRoutes() {
        return securityProperties.getPublicRoutes();
    }
}
