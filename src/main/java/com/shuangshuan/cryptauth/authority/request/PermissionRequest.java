package com.shuangshuan.cryptauth.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PermissionRequest {


    /**
     * 权限点标识
     * 校验: 权限标识不能和所有人重复，应该是唯一的标识
     */
    @NotEmpty(message = "权限标识不能为空") // 确保权限标识不能为空
    @Schema(description = "权限点标识，校验: 权限标识不能和所有人重复, 应该是唯一的标识", example = "page:view")
    private String code;

    /**
     * 权限点描述
     */
    @NotEmpty(message = "权限点描述不能为空") // 确保权限点描述不能为空
    @Size(max = 255, message = "权限点描述不能超过255个字符") // 限制描述长度
    @Schema(description = "权限点描述", example = "查看页面权限")
    private String description;

    /**
     * 权限点开启状态
     * 校验: 不能为空，且只能是 0 或 1
     */
    @NotNull(message = "权限点开启状态不能为空") // 确保开启状态不能为空
    @Schema(description = "权限点开启状态，0关闭, 1开启", example = "1")
    private String enVisible;

    /**
     * 权限点名字
     * 校验: 权限点名字不能和子集们现有的权限点名字重复
     */
    @NotEmpty(message = "权限点名字不能为空") // 确保权限点名字不能为空
    @Size(max = 100, message = "权限点名字不能超过100个字符") // 限制权限点名字的最大长度
    @Schema(description = "权限点名字，校验: 权限点名字不能和子集们现有的权限点名字重复", example = "查看页面")
    private String name;

    /**
     * 权限点父级id
     * 页面权限点pid值为'0', 按钮权限点值为所属页面权限点的id值
     */
    @NotNull(message = "父级ID不能为空") // 确保父级ID不能为空
    @Schema(description = "权限点父级id，页面权限点pid值为'0', 按钮权限点值为所属页面权限点的id值", example = "0")
    private Integer pid;

    /**
     * 权限点类型
     * 1为页面路由权限点, 2为按钮权限点
     */
    @NotNull(message = "权限点类型不能为空") // 确保权限点类型不能为空
    @Schema(description = "权限点类型，1为页面路由权限点, 2为按钮权限点", example = "1")
    private Integer type;

    /**
     * 可选的额外属性字段
     */
    @Schema(description = "其他自定义属性")
    private String additionalProperty;
}

