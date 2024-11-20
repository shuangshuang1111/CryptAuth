package com.shuangshuan.cryptauth.security.controller;

import com.shuangshuan.cryptauth.common.ResponseCode;
import com.shuangshuan.cryptauth.common.ResponseResult;
import com.shuangshuan.cryptauth.security.entity.UserAccount;
import com.shuangshuan.cryptauth.security.request.AddUserRequest;
import com.shuangshuan.cryptauth.security.request.ChangePasswordRequest;
import com.shuangshuan.cryptauth.security.response.UserDetailsResponse;
import com.shuangshuan.cryptauth.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "UserController")
@RestController
@RequestMapping("/sys")
public class UserController {


    @Autowired
    private UserService userService;  // 注入 UserService，用于修改密码等业务逻辑

    /**
     * 获取用户详细信息
     *
     * @return 用户详细信息
     */
    @Operation(summary = "details", description = "details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get user successfully")
    })
    @GetMapping("/details")
    public ResponseResult<UserDetailsResponse> getUserDetails() {
        // 获取当前登录用户的用户名
        String username = getCurrentUsername();

        // 查找用户并返回详细信息
        UserAccount user = userService.queryUserByUsername(username);

        // 将用户信息转换为 DTO（数据传输对象），返回用户详细信息
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(
                user.getUsername(),
                user.getMobile(),
                user.getCity(),
                user.getCompany(),
                user.getCompanyId(),
                user.getRoleId(),// 返回 roles 信息
                user.getStaffPhoto(),
                user.getId()
        );

        return ResponseResult.success(userDetailsResponse);
    }

    /**
     * 获取当前登录用户的用户名
     *
     * @return 当前登录用户名
     */
    private String getCurrentUsername() {
        // 从 SecurityContext 中获取当前登录用户的用户名
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    /**
     * 修改用户密码
     *
     * @param changePasswordRequest 包含旧密码和新密码
     * @return 操作结果
     */
    @Operation(summary = "updatePass", description = "updatePass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updatePass successfully"),
            @ApiResponse(responseCode = "500", description = "updatePass failed")
    })
    @PutMapping("/user/updatePass")
    public ResponseResult<String> changePassword(@Parameter(description = "changePasswordRequest") @RequestBody ChangePasswordRequest changePasswordRequest,
                                                 BindingResult bindingResult) {
        // 获取当前登录用户的用户名
        String username = getCurrentUsername();

        // 调用服务层修改密码
        try {
            boolean isPasswordChanged = userService.changePassword(username, changePasswordRequest);

            if (isPasswordChanged) {
                return ResponseResult.success(null, "Password changed successfully");
            } else {
                return ResponseResult.error(ResponseCode.ERROR.getCode(), "Failed to change password");
            }
        } catch (IllegalArgumentException e) {
            // 处理密码不正确的情况
            return ResponseResult.error(ResponseCode.ERROR.getCode(), "Old password is incorrect");
        }
    }


    /**
     * 添加新用户
     *
     * @param addUserRequest 包含新用户的详细信息
     * @return 操作结果
     */
    @Operation(summary = "addUser", description = "addUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "addUser successfully"),
            @ApiResponse(responseCode = "400", description = "Username already exists"),
            @ApiResponse(responseCode = "500", description = "Error occurred while creating use")
    })
    @PostMapping("/user/add")
    public ResponseResult<UserAccount> addUser(@Parameter(description = "addUserRequest") @RequestBody AddUserRequest addUserRequest,
                                               BindingResult bindingResult) {
        try {

            // 获取当前登录用户的用户名
            String loginUser = getCurrentUsername();
            // 调用服务层创建用户
            UserAccount userAccount = userService.addUser(addUserRequest, loginUser);

            // 返回成功响应
            return ResponseResult.success(userAccount, "User created successfully");
        } catch (IllegalArgumentException e) {
            // 处理用户名已存在的情况
            return ResponseResult.error(400, "Username already exists");
        } catch (Exception e) {
            // 处理其他未知异常
            return ResponseResult.error(500, "Error occurred while creating user");
        }
    }


}

