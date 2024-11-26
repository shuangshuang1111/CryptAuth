package com.shuangshuan.cryptauth.authority.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Schema(description = "角色表")
@Data  // Lombok 自动生成 Getter、Setter 和 toString、equals、hashCode 方法
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "角色ID", example = "1")
    private Integer id;

    @Column(unique = true)
    @Schema(description = "角色名称", example = "Admin")
    private String name;

    @Schema(description = "角色描述", example = "Administrator role with full permissions")
    private String description;

    @Schema(description = "角色状态,是否启用", example = "1")
    private Integer state;

    @Schema(description = "创建者", example = "1")
    private String createdBy;

    @Schema(description = "更新者", example = "2")
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

