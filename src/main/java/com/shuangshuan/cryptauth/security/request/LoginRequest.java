package com.shuangshuan.cryptauth.security.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

@Data
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;

    // 默认构造方法
    public LoginRequest() {
    }

    // 带参数的构造方法
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}

