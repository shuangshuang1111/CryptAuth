package com.shuangshuan.cryptauth;

import com.shuangshuan.cryptauth.security.service.UserAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserAccountServiceImplTest {

    @Autowired
    private UserAccountServiceImpl userDetailsService;

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("123456");
        System.out.println(userDetails);

    }

}
