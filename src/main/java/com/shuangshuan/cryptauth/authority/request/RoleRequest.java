package com.shuangshuan.cryptauth.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class RoleRequest {

    /**
     * 角色描述
     * 校验: 角色描述不能为空
     */
    @NotEmpty(message = "角色描述不能为空")  // 确保描述不能为空
    @Schema(description = "角色描述")
    private String description;

    /**
     * 角色名称
     * 校验: 角色名称不能为空
     */
    @NotEmpty(message = "角色名称不能为空")  // 确保名称不能为空
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色状态，默认是启用的，1启用状态，0未启用
     * 校验: 角色状态不能为空
     */
    @NotNull(message = "角色状态不能为空")  // 确保状态不能为空
    @Schema(description = "角色状态，默认是启用的,1启用状态0未启用")
    private Integer state;

    /**
     * 扩展字段，可以添加额外的属性
     * 校验: 可选字段，不进行非空校验
     */
    @Schema(description = "扩展字段，可以添加额外的属性")
    private Map<String, Object> extraProperties;

    // 可以扩展其他字段和自定义逻辑
}
