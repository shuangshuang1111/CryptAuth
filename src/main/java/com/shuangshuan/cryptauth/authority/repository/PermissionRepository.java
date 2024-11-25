package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    // 根据权限代码查找权限
    Permission findByCode(String code);

    // 根据父权限ID查询权限列表
    List<Permission> findByPid(Integer pid);
}

