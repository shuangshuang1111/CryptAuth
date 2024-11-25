package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    // 根据角色ID查找角色权限关联
    List<RolePermission> findByRoleId(Integer roleId);

    // 根据权限ID查找角色权限关联
    List<RolePermission> findByPermId(Integer permId);

    // 删除角色与权限的关联
    void deleteByRoleIdAndPermId(Integer roleId, Integer permId);

    // 根据角色ID查询关联的权限ID集合
    List<Integer> findPermissionIdsByRoleId(Integer roleId);

    void deleteByRoleId(Integer roleId);
}

