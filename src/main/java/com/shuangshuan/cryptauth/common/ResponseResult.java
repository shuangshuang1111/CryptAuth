package com.shuangshuan.cryptauth.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseResult<T> {
    // Getter 和 Setter 方法
    // 响应码
    private int code;

    private boolean success;

    // 响应数据（泛型）
    private T data;

    // 响应消息
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

    // 使用枚举 ResponseCode 生成响应
    public static <T> ResponseResult<T> success(T data) {
        ResponseCode responseCode = ResponseCode.SUCCESS;
        return new ResponseResult<>(responseCode.getCode(), data, responseCode.getMessage(),true);
    }

    // 使用枚举 ResponseCode 生成错误响应
    public static <T> ResponseResult<T> error(ResponseCode responseCode) {
        return new ResponseResult<>(responseCode.getCode(), null, responseCode.getMessage(),false);
    }

    // 静态方法，便于快速创建成功的响应
    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<>(200, data, message,true);
    }

    // 静态方法，便于快速创建失败的响应
    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<>(code, null, message,false);
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
