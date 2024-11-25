package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    // 根据权限代码查找权限
    Permission findByCode(String code);

    // 根据父权限ID查询权限列表
    List<Permission> findByPid(Integer pid);

    Optional<Permission> findById(Integer id);  // 根据 ID 查找权限

    // 你可以根据需要增加其他查询方法，如根据名称查找等
    Optional<Permission> findByName(String name);
}

