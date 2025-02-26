package vn.mos.core.advice.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.mos.core.base.BaseResponse;
import vn.mos.core.filter.RequestFilter;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final HttpServletRequest request;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // Áp dụng cho tất cả các response (ngoại trừ `BaseResponse` đã có sẵn)
        return !returnType.getParameterType().equals(BaseResponse.class);
    }

    @Override
    @ResponseBody
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String traceId = RequestFilter.getTraceId();
        String path = RequestFilter.getPath();

        if (body instanceof ResponseEntity<?> entity) {
            body = entity.getBody();
        }

        return BaseResponse.success(traceId, path, body);
    }
}
