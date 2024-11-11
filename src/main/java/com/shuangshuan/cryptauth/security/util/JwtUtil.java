package com.shuangshuan.cryptauth.security.util;

import com.shuangshuan.cryptauth.security.userdetail.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // 生成 JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 解析 JWT Token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // 获取用户名
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 检查 Token 是否过期
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 获取 Token 的过期时间
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // 验证 Token 是否有效
    public boolean validateToken(String token, User user) {
        return (user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }
}