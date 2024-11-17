package com.shuangshuan.cryptauth.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 成功的响应
    SUCCESS(200, "操作成功"),

    // 通用错误
    ERROR(500, "服务器内部错误"),

    // 资源未找到
    NOT_FOUND(404, "资源未找到"),

    // 请求参数错误
    BAD_REQUEST(400, "请求参数错误"),

    // 未授权
    UNAUTHORIZED(401, "未授权"),

    // 禁止访问
    FORBIDDEN(403, "禁止访问"),

    INVALID_CREDENTIALS(1001,"登录凭证无效");

    // 获取响应码
    private final int code;   // 响应码
    // 获取响应消息
    private final String message;  // 响应消息

    // 构造函数
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 根据响应码获取对应的枚举
    public static ResponseCode getByCode(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.getCode() == code) {
                return responseCode;
            }
        }
        return null;  // 如果没有找到对应的响应码，则返回 null
    }
}

