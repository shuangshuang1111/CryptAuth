package com.shuangshuan.cryptauth.authority.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@Schema(description = "权限点表")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "权限ID", example = "1")
    private Integer id;

    @Column(unique = true)
    @Schema(description = "权限代码", example = "PERMISSION_VIEW")
    private String code;

    @Schema(description = "权限描述", example = "View permission for the user")
    private String description;

    @Schema(description = "权限点开启状态，0关闭, 1开启. (暂时无需判断, 前端逻辑上默认全都有效直接用)", example = "1")
    private String enVisible;

    @Schema(description = "权限名称", example = "View Permission")
    private String name;

    @Schema(description = "权限点父级id，页面权限点pid值为'0', 按钮权限点值为所属页面权限点的id值", example = "0")
    private Integer pid;

    @Schema(description = "权限点类型，1为页面路由权限点, 2为按钮权限点", example = "1")
    private Integer type;

    @Schema(description = "如果是按钮权限点则对应接口地址 如果是页面权限点 则为空", example = "/sys/role/assignRoles")
    private String path;

    @Schema(description = "额外属性", example = "{\"key\": \"value\"}")
    private String properties;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "创建者", example = "1")
    private String createdBy;

    @Schema(description = "更新者", example = "2")
    private String updatedBy;

    @Schema(description = "是否已删除 ,删除为 1  未删除 0", example = "0")
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
