package com.shuangshuan.cryptauth;

import com.shuangshuan.cryptauth.security.userdetail.User;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class CryptAuthApplicationTests {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;


	@Test
	void contextLoads() {

		String tocken=JwtUtil.generateToken("123456");
		System.out.println(tocken);
		Claims claims=JwtUtil.extractClaims(tocken);
		System.out.println(claims);
		String userId=JwtUtil.extractUserId(tocken);
		System.out.println("userid"+ userId);
		boolean b=JwtUtil.isTokenExpired(tocken);
		System.out.println("是否过期"+b);
		Date date=JwtUtil.extractExpiration(tocken);
		System.out.println("expira time"+date);
		User user=new User();
		user.setId(123456L);
		boolean c=JwtUtil.validateToken(tocken,user);
		System.out.println("123456"+c);
		User user1=new User();
		user1.setId(123456777L);
		boolean d=JwtUtil.validateToken(tocken,user1);
		System.out.println("123456777"+d);


	}

}
