package com.shuangshuan.cryptauth.security.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddUserRequest {
    @NotNull
    @Schema(description = "用户名")
    private String username;  // 用户名
    @NotNull
    @Schema(description = "密码")
    private String password;  // 密码
    @Schema(description = "手机号")
    private String mobile;    // 手机号
    @Schema(description = "城市")
    private String city;      // 城市
    @Schema(description = "公司名称")
    private String company;   // 公司名称
    @Schema(description = "公司ID")
    private String companyId; // 公司ID
    @Schema(description = "角色ID")
    private String roleId;    // 角色ID
    @Schema(description = "头像")
    private String staffPhoto; // 头像
    @Schema(description = "项目名称")
    private String projectName; //项目名称
}
