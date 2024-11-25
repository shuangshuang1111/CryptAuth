package com.shuangshuan.cryptauth.authority.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PermissionRequest {

    @Schema(description = "权限点标识，校验: 权限标识不能和所有人重复, 应该是唯一的标识", example = "page:view")
    private String code;

    @Schema(description = "权限点描述", example = "查看页面权限")
    private String description;

    @Schema(description = "权限点开启状态，0关闭, 1开启", example = "1")
    private String enVisible;

    @Schema(description = "权限点名字，校验: 权限点名字不能和子集们现有的权限点名字重复", example = "查看页面")
    private String name;

    @Schema(description = "权限点父级id，页面权限点pid值为'0', 按钮权限点值为所属页面权限点的id值", example = "0")
    private Integer pid;

    @Schema(description = "权限点类型，1为页面路由权限点, 2为按钮权限点", example = "1")
    private Integer type;

    // 可选的额外属性字段
    @Schema(description = "其他自定义属性")
    private String additionalProperty;
}

