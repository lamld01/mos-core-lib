package vn.mos.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.mos.core.filter.RequestFilter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Bỏ qua các trường null khi serialize JSON
public class BaseResponse<T> {
    private String traceId;       // ID của request để debug/log
    private String path;          // API được gọi
    private int status;           // Mã HTTP status
    private String errorMessage;  // Nội dung lỗi nếu có
    private T data;               // Dữ liệu trả về

    // Phương thức tiện ích để tạo response thành công
    public static <T> BaseResponse<T> success(String traceId, String path, T data) {
        return BaseResponse.<T>builder()
            .traceId(traceId)
            .path(path)
            .status(200)
            .data(data)
            .build();
    }

    // Phương thức tiện ích để tạo response thành công
    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
            .traceId(RequestFilter.getTraceId())
            .path(RequestFilter.getPath())
            .status(200)
            .data(data)
            .build();
    }

    // Phương thức tiện ích để tạo response lỗi
    public static <T> BaseResponse<T> error(String traceId, String path, int status, String errorMessage) {
        return BaseResponse.<T>builder()
            .traceId(traceId)
            .path(path)
            .status(status)
            .errorMessage(errorMessage)
            .build();
    }
}
