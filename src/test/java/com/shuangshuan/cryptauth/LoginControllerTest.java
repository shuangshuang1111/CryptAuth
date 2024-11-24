package com.shuangshuan.cryptauth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuangshuan.cryptauth.common.ResponseCode;
import com.shuangshuan.cryptauth.security.request.LoginRequest;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class LoginControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoginSuccess() throws Exception {
        // 构造登录请求
        LoginRequest loginRequest = new LoginRequest("sa", "123456");

        // 执行登录请求并验证
        mockMvc.perform(post("/CryptAuth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())  // 期望返回 200 OK 状态// 验证返回的 token 是否为 mockToken
                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS.getCode()));  // 验证返回的状态码是否是 SUCCESS

    }

    @Test
    public void testAuthenticateUserFailure() throws Exception {

        // 构造登录请求
        LoginRequest loginRequest = new LoginRequest("sa1", "123456");

        // 执行登录请求并验证
        mockMvc.perform(post("/CryptAuth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())  // 登录失败，应该是 401
                .andExpect(jsonPath("$.message").value("登陆失败，用户名或密码错误"))
                .andExpect(jsonPath("$.code").value(ResponseCode.ERROR.getCode()));  // 验证返回的状态码是否是 SUCCESS
    }


    @Test
    public void testRegisterSuccess() throws Exception {
        String token = JwtUtil.generateToken("sa");
        mockMvc.perform(get("/register")
                        .header("Authorization", "Bearer " + token))  // 模拟带上 token
                .andExpect(status().isOk())  // 验证返回的 HTTP 状态码是 200 OK
                .andExpect(content().string("User registered successfully!"));  // 验证响应体内容是否正确
    }


}
