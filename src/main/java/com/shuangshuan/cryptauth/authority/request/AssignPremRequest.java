package com.shuangshuan.cryptauth.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AssignPremRequest {

    /**
     * 角色id
     */
    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色的ID", example = "1")
    private Integer id;

    /**
     * 权限点数组
     */
    @NotNull(message = "权限点ID数组不能为空")  // 确保permIds不能为空
    @Size(min = 1, message = "权限点ID数组必须包含至少1")  // 确保permIds的大小至少为2
    @Schema(description = "角色关联的权限点ID数组", example = "[1, 2, 3]")
    private List<Integer> permIds;

    /**
     * 其他属性（如果有）
     */
    @Schema(description = "其他属性", example = "Some additional property")
    private Object additionalProperties;
}
