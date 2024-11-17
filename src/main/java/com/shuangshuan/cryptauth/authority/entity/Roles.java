package com.shuangshuan.cryptauth.authority.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Roles {

    /**
     * 路由权限点数组，每个字符串是用项目设置的，标记英文字符串
     */
    private List<String> menus;

    /**
     * 按钮权限点数组，每个字符串是用项目设置的，标记英文字符串
     */
    private List<String> points;

    // 允许扩展其他属性
    private String additionalProperty;


}

