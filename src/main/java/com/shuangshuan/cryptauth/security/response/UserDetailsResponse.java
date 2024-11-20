package com.shuangshuan.cryptauth.security.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDetailsResponse {

    @Schema(description = "名字")
    private String username;
    @Schema(description = "手机号")
    private String mobile;
    @Schema(description = "用户的城市信息")
    private String city;
    @Schema(description = "用户所在公司名")
    private String company;
    @Schema(description = "用户所在公司id")
    private String companyId;
    @Schema(description = "用户权限点对象")
    private String roles;        // 用户权限点对象
    @Schema(description = "头像地址")
    private String staffPhoto;
    @Schema(description = "用户id")
    private Long userId;

    // 构造函数
    public UserDetailsResponse(String username, String mobile, String city, String company,
                               String companyId, String roles, String staffPhoto, Long userId) {
        this.username = username;
        this.mobile = mobile;
        this.city = city;
        this.company = company;
        this.companyId = companyId;
        this.roles = roles;
        this.staffPhoto = staffPhoto;
        this.userId = userId;
    }


}
