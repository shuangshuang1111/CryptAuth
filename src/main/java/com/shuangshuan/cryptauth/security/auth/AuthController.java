package com.shuangshuan.cryptauth.security.auth;


import com.shuangshuan.cryptauth.security.userdetail.User;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    // 用户注册
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        authService.registerUser(user);
        return "User registered successfully!";
    }

    // 用户登录，获取JWT token
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.authenticate(user);
    }
}