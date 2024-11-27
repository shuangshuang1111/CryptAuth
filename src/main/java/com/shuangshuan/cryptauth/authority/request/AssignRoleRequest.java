package com.shuangshuan.cryptauth.authority.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AssignRoleRequest {

    @Schema(description = "用户ID", example = "1")
    @NotNull(message = "用户id不饿能未空")
    private Integer id; // 用户ID

    @Schema(description = "角色ID列表", example = "1")
    @NotEmpty(message = "角色列表不能为空")
    private List<Integer> roleIds; // 角色ID列表


}

