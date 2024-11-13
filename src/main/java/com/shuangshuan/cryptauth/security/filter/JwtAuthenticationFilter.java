package com.shuangshuan.cryptauth.security.filter;


import com.shuangshuan.cryptauth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.shuangshuan.cryptauth.security.userdetail.UserAccount;
import com.shuangshuan.cryptauth.security.userdetail.UserDetailsServiceImpl;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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


    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public JwtAuthenticationFilter(UserDetailsServiceImpl userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 获取 Authorization 头部中的 Token
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {

            try {

                token = token.substring(7); // 获取 Bearer 后面的 Token 部分

                // 解析 JWT Token
                String userId = JwtUtil.extractUserId(token);
                // 如果 Token 中的用户名合法，并且 Spring Security 上下文中没有认证信息，进行认证
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                    if (JwtUtil.validateToken(token, (UserAccount) userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // 将认证信息放入 Spring Security 上下文
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        // JWT 格式错误或其他验证失败
                        jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("Invalid token") {
                        });
                        return;
                    }

                }

            } catch (ExpiredJwtException e) {
                // Token 已过期
                jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("Token expired") {
                });
                return;
            } catch (JwtException | IllegalArgumentException e) {
                // JWT 格式错误或其他验证失败
                jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("Invalid token") {
                });
                return;
            }

        } else {
            jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("The token is empty or does not start with \"Bearer\". ") {
            });
            return;
        }

        filterChain.doFilter(request, response); // 继续过滤链
    }
}