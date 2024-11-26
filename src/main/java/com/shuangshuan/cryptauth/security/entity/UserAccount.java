package com.shuangshuan.cryptauth.security.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Schema(description = "用户id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "用户手机号")
    private String mobile;

    // 用户手机号
    @Schema(description = "用户所在城市")
    private String city;

    // 用户所在城市
    @Schema(description = "用户所在公司名")
    private String company;

    @Schema(description = "用户所在公司id")
    private String companyId;

    @Schema(description = "头像")
    private String staffPhoto;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "是否已删除", example = "0")
    private Integer deleted;

    // 在实体插入时自动设置 created_at 和 created_by
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); // 设置当前时间为创建时间
        this.updatedAt = this.createdAt; // 创建时间和更新时间相同
    }

    // 在实体更新时自动设置 updated_at 和 updated_by
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now(); // 设置当前时间为更新时间
    }


}