package vn.mos.core.sercurities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import vn.mos.core.base.BaseResponse;
import vn.mos.core.filter.RequestFilter;
import vn.mos.core.utils.JsonUtils;

import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String traceId = RequestFilter.getTraceId();
        String path = RequestFilter.getPath();

        BaseResponse<Void> errorResponse = BaseResponse.error(traceId, path, HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        log.warn("🚫 Access Denied handle {}: {}", path, accessDeniedException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.getWriter().write(JsonUtils.toExactJson(errorResponse));
    }
}
