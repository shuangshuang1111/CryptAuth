package com.shuangshuan.cryptauth.security.filter;


import com.shuangshuan.cryptauth.security.entrypoint.JwtAccessDeniedHandler;
import com.shuangshuan.cryptauth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.shuangshuan.cryptauth.security.service.UserAccountServiceImpl;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // 使用 @Value 注解将配置文件中的值注入
    @Value("${server.servlet.context-path}")
    private String contextPathProperty;

    private final UserAccountServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public JwtAuthenticationFilter(UserAccountServiceImpl userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    //    Token 解析和认证流程：
//
//    提取用户名：通过 JwtUtil.extractUserName(token) 从 Token 中解析出用户名。
//    检查用户名是否合法：如果用户名为空，说明 Token 无效，可以直接跳过认证。
//    从数据库加载用户信息：通过 userDetailsService.loadUserByUsername(username) 加载用户详情。
//    验证 Token 是否有效：通过 JwtUtil.validateToken(token, userDetails) 验证 Token 是否与用户信息匹配，并确保 Token 没有过期等。
//    创建认证 Token：如果 Token 验证通过，使用 UsernamePasswordAuthenticationToken 创建认证信息，并通过 SecurityContextHolder.getContext().setAuthentication(authenticationToken)
//    放入 SecurityContextHolder。
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/CryptAuth/login") || isSwaggerRequest(request)) {
            logger.info("Skipping authentication for Swagger or login request: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        // 获取 Authorization 头部中的 Token
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            try {
                // 获取 Bearer 后面的 Token 部分
                token = token.substring(7); // 去掉 "Bearer " 前缀

                // 清空 SecurityContextHolder 中的认证信息
                SecurityContextHolder.clearContext();

                // 解析 JWT Token 获取用户名
                String username = JwtUtil.extractUserName(token);

                if (username != null) {
                    // 如果 SecurityContext 中没有认证信息（保证每次都是一个新的认证过程）
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        // 从数据库或其他存储中加载用户信息
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        // 验证 Token 是否有效
                        if (JwtUtil.validateToken(token, userDetails)) {

                            // 在将认证信息放入 SecurityContext 之前，先进行权限校验
                            if (!hasPermission(userDetails, request)) {
                                // 用户没有权限，返回 403 Forbidden
                                logger.error("Access denied for user: {} to path: {}", userDetails.getUsername(), request.getRequestURI());
                                jwtAccessDeniedHandler.handle(request, response, new AccessDeniedException("Access denied"));
                                return;
                            }
                            // 创建认证信息
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                            // 设置请求的详细信息
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            // 将认证信息放入 SecurityContextHolder
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                }
            } catch (ExpiredJwtException e) {
                logger.error("Token expired for request: {}. Error: {}", request.getRequestURI(), e.getMessage());
                // 处理 Token 过期的情况
                jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("Token expired") {
                });
                return;
            } catch (JwtException | IllegalArgumentException e) {
                logger.error("Invalid token for request: {}. Error: {}", request.getRequestURI(), e.getMessage());
                // 处理无效 Token 的情况
                jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("Invalid token") {
                });
                return;
            }
        } else {
            // 如果 Authorization 头部没有 Bearer Token 或者 Token 不合法
            logger.error("Token is missing or invalid for request: {}", request.getRequestURI());
            jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("Token is missing or invalid") {
            });
            return;
        }

// 继续执行过滤链
        filterChain.doFilter(request, response);


    }

    // 判断请求路径是否是 Swagger 的路径
    private boolean isSwaggerRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/swagger-ui") || path.contains("/v3/api-docs") || path.contains("/swagger-resources") || path.contains("/webjars");
    }

    // 检查当前用户是否具有访问该路径的权限
    private boolean hasPermission(UserDetails userDetails, HttpServletRequest request) {
        String requestPath = request.getRequestURI(); // 获取请求的路径


        // 获取上下文路径（/CryptAuth），这里通过 @Value 注解从配置文件读取
        String strippedPath = stripContextPath(requestPath);
        // 从用户的权限中检查是否包含该路径的权限
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(strippedPath));
    }

    // 辅助方法：去除请求路径中的上下文路径部分
    private String stripContextPath(String requestPath) {
        // 使用 @Value 注解获取的上下文路径
        String contextPath = contextPathProperty; // 上下文路径

        // 如果请求路径以 contextPath 开头，去掉它
        if (requestPath.startsWith(contextPath)) {
            return requestPath.substring(contextPath.length());
        }
        return requestPath; // 如果没有上下文路径前缀，返回原路径
    }
}
