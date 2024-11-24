package com.shuangshuan.cryptauth.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuangshuan.cryptauth.common.ResponseCode;
import com.shuangshuan.cryptauth.security.request.AddUserRequest;
import com.shuangshuan.cryptauth.security.request.ChangePasswordRequest;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private String token;

    // 在测试前生成一个 Token
    @BeforeEach
    public void setUp() {
        // 模拟用户登录并生成 JWT Token
        // 假设用户名为 "sa" 和密码为 "password123"
        token = JwtUtil.generateToken("sa");  // 使用你的 JwtTokenUtil 生成 token
    }

    // 测试获取用户详情接口
    @Test
    public void testGetUserDetails() throws Exception {
        mockMvc.perform(get("/sys/details")
                        .header("Authorization", "Bearer " + token))  // 将 token 添加到请求头
                .andExpect(status().isOk())  // 期望返回 200 OK
                .andExpect(jsonPath("$.data.username").value("sa"))  // 假设用户名是 sa
                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS.getCode()));  // 验证响应 code 是否为 SUCCESS
    }


    @Test
    public void testChangePasswordFailure() throws Exception {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("12345", "1234");

        mockMvc.perform(put("/sys/user/updatePass")
                        .header("Authorization", "Bearer " + token)  // 将 token 添加到请求头
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())  // 期望返回 200 OK
                .andExpect(jsonPath("$.message").value("Old password is incorrect"))
                .andExpect(jsonPath("$.code").value(ResponseCode.ERROR.getCode()));
    }

    // 测试添加新用户接口
    @Test
    public void testAddUserSuccess() throws Exception {
        AddUserRequest addUserRequest = new AddUserRequest("newuser", "password123", "1234567890", "New York", "Company A", "company123",
                "role123", "path/to/photo.jpg", "CryptAuth");

        mockMvc.perform(post("/sys/user/add")
                        .header("Authorization", "Bearer " + token)  // 将 token 添加到请求头
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addUserRequest)))
                .andExpect(status().isOk())  // 期望返回 200 OK
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS.getCode()));
    }

    @Test
    public void testAddUserFailureUsernameExists() throws Exception {
        AddUserRequest addUserRequest = new AddUserRequest("sa", "password123", "1234567890", "New York", "Company A", "company123",
                "role123", "path/to/photo.jpg", "CryptAuth");

        mockMvc.perform(post("/sys/user/add")
                        .header("Authorization", "Bearer " + token)  // 将 token 添加到请求头
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addUserRequest)))
                .andExpect(status().isOk())  // 期望返回 200 OK
                .andExpect(jsonPath("$.message").value("Username already exists"))
                .andExpect(jsonPath("$.code").value(400));
    }

    // 测试修改密码接口
    @Test
    public void testChangePasswordSuccess() throws Exception {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("123456", "1234");

        mockMvc.perform(put("/sys/user/updatePass")
                        .header("Authorization", "Bearer " + token)  // 将 token 添加到请求头
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())  // 期望返回 200 OK
                .andExpect(jsonPath("$.message").value("Password changed successfully"))
                .andExpect(jsonPath("$.code").value(ResponseCode.SUCCESS.getCode()));
    }
}
