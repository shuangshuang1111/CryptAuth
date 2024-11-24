package com.shuangshuan.cryptauth;

import com.shuangshuan.cryptauth.security.entity.UserAccount;
import com.shuangshuan.cryptauth.security.service.UserAccountServiceImpl;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

@SpringBootTest
@ActiveProfiles(value = "test")
class JwtUtilTests {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    private UserAccountServiceImpl userDetailsService;


    @Test
    void contextLoads() {

        String tocken = JwtUtil.generateToken("sa");
        System.out.println(tocken);
        Claims claims = JwtUtil.extractClaims(tocken);
        System.out.println(claims);
        String userName = JwtUtil.extractUserName(tocken);
        System.out.println("userName" + userName);
        boolean b = JwtUtil.isTokenExpired(tocken);
        System.out.println("是否过期" + b);
        Date date = JwtUtil.extractExpiration(tocken);
        System.out.println("expira time" + date);
        UserAccount user = new UserAccount();
        UserDetails userDetails = userDetailsService.loadUserByUsername("sa");
        boolean c = JwtUtil.validateToken(tocken, userDetails);
        System.out.println("sa" + c);

    }

}
