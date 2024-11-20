package com.shuangshuan.cryptauth.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @Schema(description = "用户名")
    private String username;

    @NotNull
    @Schema(description = "密码")
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

