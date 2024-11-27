package com.shuangshuan.cryptauth.security.controller;

import com.shuangshuan.cryptauth.common.BusinessResponseCode;
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
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器，处理用户登录及注册请求
 */
@Tag(name = "LoginController", description = "LoginController")
@RestController
@RequestMapping
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationService authService;

    /**
     * 用户登录接口
     *
     * @param loginRequest  登录请求对象，包含用户名和密码
     * @param bindingResult 校验结果对象，确保请求参数的有效性
     * @return 登录成功时返回JWT Token，登录失败时返回错误信息
     */
    @Operation(summary = "login", description = "User login. Authenticate and return JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successfully"),
            @ApiResponse(responseCode = "4001", description = "Login failed, invalid credentials")
    })
    @PostMapping("/login")
    public ResponseResult<String> login(@Parameter(description = "Login request object containing username and password")
                                        @RequestBody @Valid LoginRequest loginRequest,
                                        BindingResult bindingResult) {

        logger.info("Login request received with parameters: {}", loginRequest);

        // 调用认证服务生成 JWT Token
        String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        // 如果 Token 为 null 或空，表示认证失败
        if (token == null || token.isEmpty()) {
            return ResponseResult.error(BusinessResponseCode.LOGIN_FAILED);
        }

        // 返回生成的JWT Token
        return ResponseResult.success(token);
    }

    /**
     * 用户注册接口（示例接口，实际注册逻辑未实现）
     *
     * @return 注册成功的响应消息
     */
    @Operation(summary = "register", description = "User registration endpoint. This is a placeholder for user registration logic.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register successfully")
    })
    @GetMapping("/register")
    public String register() {
        return "User registered successfully!";
    }
}
