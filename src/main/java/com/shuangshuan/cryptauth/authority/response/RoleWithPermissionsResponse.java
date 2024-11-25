package com.shuangshuan.cryptauth.authority.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RoleWithPermissionsResponse {

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "角色ID")
    private Integer id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色关联的权限ID集合")
    private List<Integer> permIds;

    @Schema(description = "角色状态")
    private Integer state;

    // 可以扩展其他字段
}
