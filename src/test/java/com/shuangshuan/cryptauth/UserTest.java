package com.shuangshuan.cryptauth;

import com.shuangshuan.cryptauth.security.userdetail.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername() {
        UserDetails userDetails=userDetailsService.loadUserByUsername("123456");
        System.out.println(userDetails);

    }

}
