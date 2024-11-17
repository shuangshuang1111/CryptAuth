package com.shuangshuan.cryptauth.security.request;


import lombok.Data;

@Data
public class AddUserRequest {
    private String username;  // 用户名
    private String password;  // 密码
    private String mobile;    // 手机号
    private String city;      // 城市
    private String company;   // 公司名称
    private String companyId; // 公司ID
    private String roleId;    // 角色ID
    private String staffPhoto; // 头像
    private String projectName; //项目名称
}
