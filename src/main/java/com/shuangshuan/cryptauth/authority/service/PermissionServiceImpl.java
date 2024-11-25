package com.shuangshuan.cryptauth.authority.service;

import com.shuangshuan.cryptauth.authority.entity.Permission;
import com.shuangshuan.cryptauth.authority.entity.RolePermission;
import com.shuangshuan.cryptauth.authority.repository.PermissionRepository;
import com.shuangshuan.cryptauth.authority.repository.RolePermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionsRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    // 获取所有权限点
    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }

    // 根据 ID 查找权限点
    public Optional<Permission> findPermissionById(Integer id) {
        return permissionRepository.findById(id);
    }

    // 创建新的权限点
    @Transactional
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    // 判断权限是否存在
    public boolean existsById(Integer id) {
        return permissionRepository.existsById(id);
    }

    // 删除权限点
    @Transactional
    public boolean deleteById(Integer id) {
        if (!permissionRepository.existsById(id)) {
            return false;
        }
        permissionRepository.deleteById(id);
        return true;
    }

    /**
     * 为角色分配权限
     *
     * @param roleId  角色ID
     * @param permIds 权限ID列表
     */
    @Transactional
    public Boolean assignPermissionsToRole(Integer roleId, List<Integer> permIds) {
        try {
            // 1. 删除角色已有的权限关联
            rolePermissionsRepository.deleteByRoleId(roleId);

            // 2. 如果传递了权限ID列表，插入新的权限关联
            if (permIds != null && !permIds.isEmpty()) {
                List<RolePermission> rolePermissionsList = new ArrayList<>();
                for (Integer permId : permIds) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(roleId);
                    rolePermission.setPermId(permId);
                    rolePermissionsList.add(rolePermission);
                }

                // 批量保存角色与权限的关联
                rolePermissionsRepository.saveAll(rolePermissionsList);
            }

            // 返回 true，表示角色权限分配成功
            return true;
        } catch (Exception e) {
            // 处理异常并返回 false，表示分配权限失败
            e.printStackTrace();
            return false;
        }
    }
}
