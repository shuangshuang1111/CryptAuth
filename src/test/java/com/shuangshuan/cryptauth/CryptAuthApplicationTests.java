package com.shuangshuan.cryptauth;

import com.shuangshuan.cryptauth.security.userdetail.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class CryptAuthApplicationTests {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(String userId) {
		return Jwts.builder()
				.setSubject(userId)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public Claims extractClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}

	// 获取用户userid
	public String extractUserId(String token) {
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
		return (user.getId().toString().equals(extractUserId(token)) && !isTokenExpired(token));
	}

	@Test
	void contextLoads() {

		String tocken=generateToken("123456");
		System.out.println(tocken);
		Claims claims=extractClaims(tocken);
		System.out.println(claims);
		String userId=extractUserId(tocken);
		System.out.println("userid"+ userId);
		boolean b=isTokenExpired(tocken);
		System.out.println("是否过期"+b);
		Date date=extractExpiration(tocken);
		System.out.println("expira time"+date);
		User user=new User();
		user.setId(123456L);
		boolean c=validateToken(tocken,user);
		System.out.println("123456"+c);
		User user1=new User();
		user1.setId(123456777L);
		boolean d=validateToken(tocken,user1);
		System.out.println("123456777"+d);



	}

}
