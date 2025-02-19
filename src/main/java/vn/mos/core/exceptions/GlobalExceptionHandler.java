package vn.mos.core.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import vn.mos.core.base.BaseResponse;
import vn.mos.core.filter.TraceIdFilter;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BaseResponse<Void>> handleBusinessException(BusinessException ex) {
    String traceId = TraceIdFilter.getTraceId();
    String path = TraceIdFilter.getPath();

    log.warn("⚠️ Business Error [{}]: {}", ex.getErrorCode(), ex.getMessage());

    return ResponseEntity.status(ex.getStatus())
        .body(BaseResponse.error(traceId, path, ex.getStatus().value(), ex.getMessage()));
  }

  @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
  public ResponseEntity<BaseResponse<Void>> handleNotFound(Exception e) {
    String traceId = TraceIdFilter.getTraceId();
    String path = TraceIdFilter.getPath();

    log.warn("🔍 Not Found: {}", path);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(BaseResponse.error(traceId, path, 404, "The requested resource was not found"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<Void>> handleGeneralError(Exception ex) {
    String traceId = TraceIdFilter.getTraceId();
    String path = TraceIdFilter.getPath();

    log.error("❌ Error at {}: {}", path, ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error(traceId, path, 500, "Internal Server Error"));
  }

}
