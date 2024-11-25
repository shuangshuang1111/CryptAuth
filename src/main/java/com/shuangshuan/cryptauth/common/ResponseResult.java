package com.shuangshuan.cryptauth.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseResult<T> {
    // Getter 和 Setter 方法
    // 响应码
    @Schema(description = "返回码")
    private int code;

    @Schema(description = "运行结果")
    private boolean success;

    // 响应数据（泛型）
    @Schema(description = "响应数据（泛型）")
    private T data;

    // 响应消息
    @Schema(description = "响应消息")
    private String message;

    // 构造方法
    public ResponseResult(int code, T data, String message, boolean success) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
    }

    // 默认构造方法
    public ResponseResult() {
    }

    // 使用系统返回码生成响应
    public static <T> ResponseResult<T> success(T data) {
        SystemResponseCode responseCode = SystemResponseCode.SUCCESS;
        return new ResponseResult<>(responseCode.getCode(), data, responseCode.getMessage(), true);
    }

    public static <T> ResponseResult<T> error(SystemResponseCode responseCode) {
        return new ResponseResult<>(responseCode.getCode(), null, responseCode.getMessage(), false);
    }

    // 使用业务返回码生成响应
    public static <T> ResponseResult<T> success(T data, BusinessResponseCode responseCode) {
        return new ResponseResult<>(responseCode.getCode(), data, responseCode.getMessage(), true);
    }

    public static <T> ResponseResult<T> error(BusinessResponseCode responseCode) {
        return new ResponseResult<>(responseCode.getCode(), null, responseCode.getMessage(), false);
    }

    // 静态方法，便于快速创建成功的响应
    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<>(SystemResponseCode.SUCCESS.getCode(), data, message, true);
    }

    // 静态方法，便于快速创建失败的响应
    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<>(code, null, message, false);
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
