package com.shuangshuan.cryptauth.security.util;

import com.shuangshuan.cryptauth.security.userdetail.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static String secret;
    private static long expiration;

    @Value("${jwt.secret}")
    private String injectedSecret;

    @Value("${jwt.expiration}")
    private long injectedExpiration;

    @PostConstruct
    public void init() {
        secret = injectedSecret;
        expiration = injectedExpiration;
    }



    // 生成 JWT Token
    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 解析 JWT Token
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // 获取用户userid
    public static String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }

    // 检查 Token 是否过期
    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 获取 Token 的过期时间
    public static Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // 验证 Token 是否有效
    public static boolean validateToken(String token, User user) {
        return (user.getId().toString().equals(extractUserId(token)) && !isTokenExpired(token));
    }
}