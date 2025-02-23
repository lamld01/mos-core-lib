package vn.mos.core.sercurities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.mos.core.base.BaseResponse;
import vn.mos.core.filter.RequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.mos.core.utils.JsonUtils;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String traceId = RequestFilter.getTraceId();
        String path = RequestFilter.getPath();

        BaseResponse<Void> errorResponse = BaseResponse.error(traceId, path, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + authException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JsonUtils.toExactJson(errorResponse));
    }
}
