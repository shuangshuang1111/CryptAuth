package com.shuangshuan.cryptauth.authority.service;

import com.shuangshuan.cryptauth.authority.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Boolean assignPermissionsToRole(Integer roleId, List<Integer> permIds);

    // 获取所有权限点
    List<Permission> findAllPermissions();

    // 根据 ID 查找权限点
    Optional<Permission> findPermissionById(Integer id);

    // 创建新的权限点
    Permission save(Permission permission);

    // 判断权限是否存在
    boolean existsById(Integer id);

    // 删除权限点
    void deleteById(Integer id);

    // 根据 ID 查找权限点
    Optional<Permission> findPermissionByCode(String code);
}
