package com.shuangshuan.cryptauth.security.controller;

import com.shuangshuan.cryptauth.common.ResponseCode;
import com.shuangshuan.cryptauth.common.ResponseResult;
import com.shuangshuan.cryptauth.security.request.LoginRequest;
import com.shuangshuan.cryptauth.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "LoginController", description = "LoginController")
@RestController
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationService authService;


    @Operation(summary = "login", description = "login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login successfully"),
            @ApiResponse(responseCode = "500", description = "login failed")
    })
    @PostMapping("/login")
    public ResponseResult<String> login(@Parameter(description = "loginRequest")
                                        @RequestBody @Valid LoginRequest loginRequest,
                                        BindingResult bindingResult) {

        logger.info("request请求参数为:{} ", loginRequest);
        // 调用认证服务生成 JWT Token
        String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        // 如果 Token 为 null 或空，表示认证失败
        if (token == null || token.isEmpty()) {
            return ResponseResult.error(ResponseCode.ERROR.getCode(), "登陆失败，用户名或密码错误");
        }

        return ResponseResult.success(token);

    }


    @GetMapping("/register")
    @Operation(summary = "register", description = "register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "register successfully")
    })
    public String register() {
        return "User registered successfully!";
    }
}