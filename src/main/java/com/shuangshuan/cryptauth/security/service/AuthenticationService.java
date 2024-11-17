package com.shuangshuan.cryptauth.security.service;

import com.shuangshuan.cryptauth.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAccountServiceImpl userDetailsService;

    // 认证用户并生成 JWT Token
    public String authenticateUser(String username, String password) {
        try {
            // Step 1: 加载用户信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Step 2: 使用用户信息和密码进行认证
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, password, userDetails.getAuthorities()
            );

            // 使用 AuthenticationManager 来执行认证
            authenticationManager.authenticate(authenticationToken);  // 如果认证失败，会抛出异常

            // Step 3: 认证通过，生成 JWT Token
            return "Bearer " + JwtUtil.generateToken(userDetails.getUsername());  // 生成 JWT Token

        } catch (Exception e) {
            // 如果认证失败（用户名或密码错误），返回 null
            return null;
        }
    }
}


