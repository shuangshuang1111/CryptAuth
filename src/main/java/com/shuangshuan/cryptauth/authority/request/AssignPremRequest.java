package com.shuangshuan.cryptauth.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AssignPremRequest {

    /**
     * 角色id
     */
    @Schema(description = "角色的ID", example = "1")
    private Integer id;

    /**
     * 权限点数组
     */
    @Schema(description = "角色关联的权限点ID数组", example = "[1, 2, 3]")
    private List<Integer> permIds;

    /**
     * 其他属性（如果有）
     */
    @Schema(description = "其他属性", example = "Some additional property")
    private Object additionalProperties;
}
