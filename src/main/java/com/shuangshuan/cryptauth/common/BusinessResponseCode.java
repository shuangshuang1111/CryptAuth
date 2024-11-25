package com.shuangshuan.cryptauth.common;

import lombok.Getter;

@Getter
public enum BusinessResponseCode {

    // 角色相关的错误
    ROLE_NOT_FOUND(1001, "角色未找到"),
    ROLE_CREATION_FAILED(1002, "角色创建失败"),
    ROLE_UPDATE_FAILED(1003, "角色更新失败"),
    ROLE_DELETE_FAILED(1004, "角色删除失败"),
    ROLE_PERMISSION_ASSIGN_FAILED(1005, "角色分配失败"),

    // 角色相关的正确
    ROLE_CREATED_SUCCESS(1100, "角色创建成功"),
    ROLE_UPDATED_SUCCESS(1101, "角色更新成功"),
    ROLE_DELETED_SUCCESS(1102, "角色删除成功"),
    ROLE_LIST_FETCHED_SUCCESS(1103, "角色列表获取成功"),
    ROLE_FETCHED_SUCCESS(1104, "角色获取成功"),
    ROLE_PERMISSIONS_ASSIGNED_SUCCESS(1105, "角色分配成功"),

    // 用户相关的错误
    USER_NOT_FOUND(2001, "用户未找到"),
    USER_CREATION_FAILED(2002, "用户创建失败"),
    USER_UPDATE_FAILED(2003, "用户更新失败"),
    USER_DELETE_FAILED(2004, "用户删除失败"),

    // 用户相关的正确
    USER_CREATED_SUCCESS(2100, "用户创建成功"),
    USER_UPDATED_SUCCESS(2101, "用户更新成功"),
    USER_DELETED_SUCCESS(2102, "用户删除成功"),
    USER_LIST_FETCHED_SUCCESS(2103, "用户列表获取成功"),
    USER_FETCHED_SUCCESS(2104, "用户获取成功"),

    // 权限相关的错误
    PERMISSION_DENIED(3001, "权限不足"),

    // 权限相关的正确
    PERMISSION_GRANTED_SUCCESS(3100, "权限授权成功"),
    PERMISSION_REVOKED_SUCCESS(3101, "权限撤销成功");

    private final int code;
    private final String message;

    BusinessResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
