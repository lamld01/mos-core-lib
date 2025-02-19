package vn.mos.core.base.type;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Danh sách các mã lỗi chung dùng cho toàn hệ thống.
 */
@Getter
public enum BusinessErrorCode {

    BAD_REQUEST("COMMON_400", "Bad request", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("COMMON_401", "Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("COMMON_403", "Forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND("COMMON_404", "Resource not found", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("COMMON_405", "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED),
    CONFLICT("COMMON_409", "Conflict", HttpStatus.CONFLICT),
    UNSUPPORTED_MEDIA_TYPE("COMMON_415", "Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    INTERNAL_SERVER_ERROR("COMMON_500", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE("COMMON_503", "Service unavailable", HttpStatus.SERVICE_UNAVAILABLE);

    private final String code;
    private final String message;
    private final HttpStatus status;

    BusinessErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
