package com.shuangshuan.cryptauth.security.entrypoint;

import com.shuangshuan.cryptauth.security.util.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    // 返回结果的json处理什么的   有的字段不适合序列化，redistribution   没有权限也需要加入一个接口
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String originalUri = SecurityUtils.getOriginalRequestUri(request);
        // 记录日志，帮助排查问题
        logger.error("Unauthorized access attempt to URL: {}", originalUri);

        // 设置响应内容类型为 JSON
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 创建返回的错误消息
        String errorMessage = String.format("{\"error\": \"Unauthorized\", \"message\": \"Access is denied due to invalid credentials.\"," +
                " \"path\": \"%s\", \"code\": 401}", originalUri);

        // 返回 JSON 格式的错误消息
        response.getWriter().write(errorMessage);
    }
}