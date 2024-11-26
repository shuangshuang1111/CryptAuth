package com.shuangshuan.cryptauth.security.controller;

import com.shuangshuan.cryptauth.common.BusinessResponseCode;
import com.shuangshuan.cryptauth.common.ResponseResult;
import com.shuangshuan.cryptauth.security.entity.UserAccount;
import com.shuangshuan.cryptauth.security.request.AddUserRequest;
import com.shuangshuan.cryptauth.security.request.ChangePasswordRequest;
import com.shuangshuan.cryptauth.security.response.UserDetailsResponse;
import com.shuangshuan.cryptauth.security.service.UserService;
import com.shuangshuan.cryptauth.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
        String username = SecurityUtils.getCurrentUsername();

        // 查找用户并返回详细信息
        UserAccount user = userService.queryUserByUsername(username);

        // 将用户信息转换为 DTO（数据传输对象），返回用户详细信息
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(
                user.getUsername(),
                user.getMobile(),
                user.getCity(),
                user.getCompany(),
                user.getCompanyId(),
                null,
                user.getStaffPhoto(),
                user.getId()
        );

        return ResponseResult.success(userDetailsResponse);
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
            @ApiResponse(responseCode = "2001", description = "user not find"),
            @ApiResponse(responseCode = "5001", description = "updatePass failed"),
            @ApiResponse(responseCode = "5002", description = "old password incorrect")
    })
    @PutMapping("/user/updatePass")
    public ResponseResult<String> changePassword(@Parameter(description = "changePasswordRequest") @RequestBody ChangePasswordRequest changePasswordRequest,
                                                 BindingResult bindingResult) {
        // 获取当前登录用户的用户名
        String username = SecurityUtils.getCurrentUsername();

        // 调用服务层修改密码
        try {
            boolean isPasswordChanged = userService.changePassword(username, changePasswordRequest);

            if (isPasswordChanged) {
                return ResponseResult.success(null, BusinessResponseCode.PASSWORD_UPDATE_SUCCESS.getMessage());
            } else {
                return ResponseResult.error(BusinessResponseCode.PASSWORD_UPDATE_FAILED);
            }
        } catch (IllegalArgumentException e) {
            // 处理密码不正确的情况
            return ResponseResult.error(BusinessResponseCode.OLD_PASSWORD_INCORRECT);
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
            @ApiResponse(responseCode = "2002", description = "Error occurred while creating use")
    })
    @PostMapping("/user/add")
    public ResponseResult<UserAccount> addUser(@Parameter(description = "addUserRequest") @RequestBody AddUserRequest addUserRequest,
                                               BindingResult bindingResult) {
        try {

            // 获取当前登录用户的用户名
            String loginUser = SecurityUtils.getCurrentUsername();
            // 调用服务层创建用户
            UserAccount userAccount = userService.addUser(addUserRequest, loginUser);

            // 返回成功响应
            return ResponseResult.success(userAccount, BusinessResponseCode.USER_CREATED_SUCCESS.getMessage());
        } catch (IllegalArgumentException e) {
            // 处理用户名已存在的情况
            return ResponseResult.error(BusinessResponseCode.USERNAME_ALREADY_EXISTS_FAILED);
        } catch (Exception e) {
            // 处理其他未知异常
            return ResponseResult.error(BusinessResponseCode.USER_CREATION_FAILED);
        }
    }


}

