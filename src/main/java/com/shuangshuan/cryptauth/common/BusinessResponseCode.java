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
    ROLE_ALREADY_EXISTS_FAILED(1006, "角色已经存在"),
    ROLE_ASSIGNMENT_FAILED(1007, "角色分配给用户失败"),

    // 角色相关的正确
    ROLE_CREATED_SUCCESS(1100, "角色创建成功"),
    ROLE_UPDATED_SUCCESS(1101, "角色更新成功"),
    ROLE_DELETED_SUCCESS(1102, "角色删除成功"),
    ROLE_LIST_FETCHED_SUCCESS(1103, "角色列表获取成功"),
    ROLE_FETCHED_SUCCESS(1104, "角色获取成功"),
    ROLE_PERMISSIONS_ASSIGNED_SUCCESS(1105, "权限分配给角色成功"),
    ROLES_ASSIGNED_TO_USER_SUCCESS(1105, "角色分配给用户成功"),

    // 用户相关的错误
    USER_NOT_FOUND(2001, "用户未找到"),
    USER_CREATION_FAILED(2002, "用户创建失败"),
    USER_UPDATE_FAILED(2003, "用户更新失败"),
    USER_DELETE_FAILED(2004, "用户删除失败"),
    USERNAME_ALREADY_EXISTS_FAILED(2005, "用户名已存在"),

    // 用户相关的正确
    USER_CREATED_SUCCESS(2100, "用户创建成功"),
    USER_UPDATED_SUCCESS(2101, "用户更新成功"),
    USER_DELETED_SUCCESS(2102, "用户删除成功"),
    USER_LIST_FETCHED_SUCCESS(2103, "用户列表获取成功"),
    USER_FETCHED_SUCCESS(2104, "用户获取成功"),

    // 权限相关的错误
    PERMISSION_DENIED(3001, "权限不足"),
    PERMISSION_CREATION_FAILED(3001, "权限点创建失败"),
    PERMISSION_UPDATE_FAILED(3002, "权限点更新失败"),
    PERMISSION_DELETE_FAILED(3003, "权限点删除失败"),
    PERMISSION_NOT_FOUND(3004, "权限点未找到"),
    PERMISSION_GRANTED_FAILED(3005, "权限授权失败"),
    PERMISSION_CODE_ALREADY_EXISTS(3006, "该权限码已经存在"),


    // 权限相关的正确
    PERMISSION_GRANTED_SUCCESS(3100, "权限授权成功"),
    PERMISSION_REVOKED_SUCCESS(3101, "权限撤销成功"),
    PERMISSION_FETCHED_SUCCESS(3102, "权限点信息获取成功"),
    PERMISSION_CREATED_SUCCESS(3103, "权限点创建成功"),
    PERMISSION_UPDATED_SUCCESS(3104, "权限点更新成功"),
    PERMISSION_DELETED_SUCCESS(3105, "权限点删除成功"),
    PERMISSION_LIST_FETCHED_SUCCESS(3106, "权限点列表获取成功"),


    // 用户登录相关的错误
    LOGIN_FAILED(4001, "登录失败，用户名或密码错误"),
    USERNAME_ALREADY_EXISTS(4002, "用户名已存在"),

    // 登录相关的成功
    LOGIN_SUCCESS(4100, "登录成功"),

    // 修改密码相关的错误
    PASSWORD_UPDATE_FAILED(5001, "密码修改失败"),
    OLD_PASSWORD_INCORRECT(5002, "旧密码不正确"),
    PASSWORD_UPDATE_SUCCESS(5100, "密码修改成功");

    private final int code;
    private final String message;

    BusinessResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
