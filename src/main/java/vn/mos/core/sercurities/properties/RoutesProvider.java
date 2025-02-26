package vn.mos.core.sercurities.properties;

import java.util.List;

public interface RoutesProvider {
    List<String> getPublicRoutes();
    List<String> getCorsAllowUrl();
}
