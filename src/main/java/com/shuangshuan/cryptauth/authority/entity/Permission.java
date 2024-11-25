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

    @Schema(description = "权限代码", example = "PERMISSION_VIEW")
    private String code;

    @Schema(description = "权限描述", example = "View permission for the user")
    private String description;

    @Schema(description = "是否对外可见", example = "1")
    private Boolean enVisible;

    @Schema(description = "权限名称", example = "View Permission")
    private String name;

    @Schema(description = "父权限ID", example = "0")
    private Integer pid;

    @Schema(description = "权限类型", example = "1")
    private Integer type;

    @Schema(description = "额外属性", example = "{\"key\": \"value\"}")
    private String properties;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "创建者ID", example = "1")
    private Integer createdBy;

    @Schema(description = "更新者ID", example = "2")
    private Integer updatedBy;

    @Schema(description = "是否已删除", example = "0")
    private Boolean deleted;

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
