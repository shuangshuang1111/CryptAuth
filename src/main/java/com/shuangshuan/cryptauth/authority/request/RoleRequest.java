package com.shuangshuan.cryptauth.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class RoleRequest {

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色状态，默认是启用的,1启用状态0未启用")
    private Integer state;

    @Schema(description = "扩展字段，可以添加额外的属性")
    private Map<String, Object> extraProperties;

    // 可以扩展其他字段和自定义逻辑
}
