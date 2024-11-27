package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.Permission;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findById(Integer id);  // 根据 ID 查找权限

    // 查询 deleted = 0 的权限
    @Query("SELECT p FROM Permission p WHERE p.deleted = 0")
    List<Permission> findAllActivePermissions();

    // 通过 code 查找 Permission
    Optional<Permission> findByCode(String code);

    // 在 PermissionRepository 中使用 @Modifying 和 @Query @Modifying 注解的更新操作（例如使用 @Query 执行的更新或删除操作）需要一个事务来保证数据一致性，
    // 否则会抛出 TransactionRequiredException 异常。
    @Modifying
    @Query("UPDATE Permission p SET p.deleted = 1 WHERE p.id = :id")
    void softDeleteById(@Param("id") Integer id);

    // 根据权限ID列表查询所有权限信息
    List<Permission> findByIdIn(List<Integer> permissionIds);

}

