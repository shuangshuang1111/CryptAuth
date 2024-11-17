package com.shuangshuan.cryptauth.security.response;

import com.shuangshuan.cryptauth.authority.entity.Roles;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDetailsResponse {

    private String username;
    private String mobile;
    private String city;
    private String company;
    private String companyId;
    private String roles;        // 用户权限点对象
    private String staffPhoto;
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
