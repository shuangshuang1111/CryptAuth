package com.shuangshuan.cryptauth.security.entrypoint;

import com.shuangshuan.cryptauth.security.util.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(JwtAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String originalUri = SecurityUtils.getOriginalRequestUri(request);
        // 记录日志，帮助排查问题
        logger.error("Access Denied attempt to URL: {}", originalUri);
        // 设置响应类型为 JSON
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // 返回 JSON 格式的错误消息
        String errorMessage = String.format("{\"error\": \"Access Denied\", \"message\": \"You do not have permission to access this resource.\", " +
                "\"code\": 403, \"url\": \"%s\"}", originalUri);

        response.getWriter().write(errorMessage);
    }
}

