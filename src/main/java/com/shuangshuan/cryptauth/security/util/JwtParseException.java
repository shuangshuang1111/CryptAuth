package com.shuangshuan.cryptauth.security.util;

public class JwtParseException extends Exception {

    // 默认构造函数
    public JwtParseException() {
        super();
    }

    // 传递异常消息的构造函数
    public JwtParseException(String message) {
        super(message);
    }

    // 传递异常消息和原始异常的构造函数
    public JwtParseException(String message, Throwable cause) {
        super(message, cause);
    }

    // 仅传递原始异常的构造函数
    public JwtParseException(Throwable cause) {
        super(cause);
    }
}

