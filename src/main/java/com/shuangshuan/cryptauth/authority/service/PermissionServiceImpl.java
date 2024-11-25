package com.shuangshuan.cryptauth.authority.service;

import com.shuangshuan.cryptauth.authority.entity.RolePermission;
import com.shuangshuan.cryptauth.authority.repository.RolePermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionsRepository;

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
