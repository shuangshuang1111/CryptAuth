package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // 根据 ID 查找角色
    Optional<Role> findById(Integer id);

    // 查找所有启用的角色
    List<Role> findByState(Integer state);

    // 判断角色是否存在
    boolean existsById(Integer id);

    // 删除角色
    void deleteById(Integer id);

    Optional<Role> findByName(String name);

    @Modifying
    @Query("UPDATE Role p SET p.deleted = 1 WHERE p.id = :id")
    void softDeleteById(@Param("id") Integer id);

}

