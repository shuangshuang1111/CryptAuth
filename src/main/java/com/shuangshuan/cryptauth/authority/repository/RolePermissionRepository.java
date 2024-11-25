package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    // 根据角色ID查询关联的权限ID集合
    List<Integer> findPermissionIdsByRoleId(Integer roleId);

    void deleteByRoleId(Integer roleId);
}

