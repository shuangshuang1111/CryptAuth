package com.shuangshuan.cryptauth;

import com.shuangshuan.cryptauth.common.ResponseCode;
import com.shuangshuan.cryptauth.common.ResponseResult;
import com.shuangshuan.cryptauth.security.controller.UserController;
import com.shuangshuan.cryptauth.security.entity.UserAccount;
import com.shuangshuan.cryptauth.security.request.ChangePasswordRequest;
import com.shuangshuan.cryptauth.security.response.UserDetailsResponse;
import com.shuangshuan.cryptauth.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class UserControllerTest {

    @Mock
    private UserService userService; // Mock the UserService

    @InjectMocks
    private UserController userController; // Inject mock into UserController

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void getUserDetails() {
        // Arrange
        String username = "testuser";
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword("1234");
        userAccount.setMobile("123456789");
        userAccount.setCity("CityName");
        userAccount.setCompany("CompanyName");
        userAccount.setCompanyId("1234");
        userAccount.setRoleId("1");
        userAccount.setStaffPhoto("photo.jpg");
        userAccount.setId(2L);

        when(userService.queryUserByUsername(username)).thenReturn(userAccount);

        // Simulate a logged-in user
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication().getPrincipal()).thenReturn(new User(username, "", new ArrayList<>()));

        // Act
        ResponseResult<UserDetailsResponse> result = userController.getUserDetails();

        // Assert
        assertNotNull(result);
        assertEquals(ResponseCode.SUCCESS.getCode(), result.getCode());
        assertEquals(username, result.getData().getUsername());
        assertEquals("123456789", result.getData().getMobile());
        assertEquals("CityName", result.getData().getCity());
    }

    @Test
    void changePassword_Success() {
        // Arrange
        String username = "testuser";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("1234", "123456");

        when(userService.changePassword(username, changePasswordRequest)).thenReturn(true);

        // Simulate a logged-in user
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication().getPrincipal()).thenReturn(new User(username, "", new ArrayList<>()));

        // Act
        ResponseResult<String> result = userController.changePassword(changePasswordRequest);

        // Assert
        assertNotNull(result);
        assertEquals(ResponseCode.SUCCESS.getCode(), result.getCode());
        assertEquals("Password changed successfully", result.getMessage());
    }

    @Test
    void changePassword_Failure() {
        // Arrange
        String username = "testuser";
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("wrongoldpassword", "newpassword");

        when(userService.changePassword(username, changePasswordRequest)).thenThrow(new IllegalArgumentException("Old password is incorrect"));

        // Simulate a logged-in user
        SecurityContext context = mock(SecurityContext.class);
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication().getPrincipal()).thenReturn(new User(username, "", new ArrayList<>()));

        // Act
        ResponseResult<String> result = userController.changePassword(changePasswordRequest);

        // Assert
        assertNotNull(result);
        assertEquals(ResponseCode.ERROR.getCode(), result.getCode());
        assertEquals("Old password is incorrect", result.getMessage());
    }
}
