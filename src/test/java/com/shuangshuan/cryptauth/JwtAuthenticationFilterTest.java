package com.shuangshuan.cryptauth;


import com.shuangshuan.cryptauth.security.filter.JwtAuthenticationFilter;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private String validToken;

    @BeforeEach
    public void setUp() {
        validToken = JwtUtil.generateToken("123456");
        System.out.println("前置生成的有效token值为：" + validToken);
    }

    @Test
    @WithMockUser(username = "sa")
    public void testJwtAuthenticationFilter_withValidToken() throws Exception {
        // 模拟请求并使用有效的 JWT Token
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/register")
                        .header("Authorization", "Bearer " + validToken))  // 模拟传递 Token
                .andExpect(MockMvcResultMatchers.status().isOk()); // 验证响应状态码
    }

    @Test
    public void testJwtAuthenticationFilter_withInvalidToken() throws Exception {
        // 使用无效的 JWT Token
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/register")
                        .header("Authorization", "Bearer YiLCJpYXQiOjE3MzE0OTEzMjIsImV4cCI6"))  // 模拟传递无效 Token
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());  // 预期返回 401 Unauthorized
    }

    @Test
    public void testJwtAuthenticationFilter_withoutToken() throws Exception {
        // 未传递 Token
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/register"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());  // 预期返回 401 Unauthorized
    }
}
