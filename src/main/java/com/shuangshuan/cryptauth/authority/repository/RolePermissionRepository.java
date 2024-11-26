package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    // 根据角色ID查询关联的权限ID集合
    @Query("SELECT rp.id FROM RolePermission rp WHERE rp.roleId = :roleId")
    List<Integer> findPermissionIdsByRoleId(@Param("roleId") Integer roleId);


    void deleteByRoleId(Integer roleId);
}

