package com.shuangshuan.cryptauth.security.util;

import com.shuangshuan.cryptauth.security.userdetail.UserAccount;
import io.jsonwebtoken.*;
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
    public static Claims extractClaims(String token) throws JwtParseException{

        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Token 过期异常
            throw new JwtParseException("Token has expired", e);
        } catch (UnsupportedJwtException e) {
            // 不支持的 JWT 类型异常
            throw new JwtParseException("Unsupported token", e);
        } catch (MalformedJwtException e) {
            // 无效的 JWT 格式异常
            throw new JwtParseException("Malformed token", e);
        } catch (SignatureException e) {
            // 签名验证失败异常
            throw new JwtParseException("Invalid token signature", e);
        } catch (Exception e) {
            // 其他未知的异常
            throw new JwtParseException("Token parsing failed", e);
        }

    }

    // 获取用户userid
    public static String extractUserId(String token) throws JwtParseException {
        return extractClaims(token).getSubject();
    }

    // 检查 Token 是否过期
    public static boolean isTokenExpired(String token) throws JwtParseException {
        return extractExpiration(token).before(new Date());
    }

    // 获取 Token 的过期时间
    public static Date extractExpiration(String token) throws JwtParseException {
        return extractClaims(token).getExpiration();
    }

    // 验证 Token 是否有效

    public static boolean validateToken(String token, UserAccount user) throws JwtParseException {
        return (user.getId().toString().equals(extractUserId(token)) && !isTokenExpired(token));
    }

    // 自定义的 JWT 解析异常

}