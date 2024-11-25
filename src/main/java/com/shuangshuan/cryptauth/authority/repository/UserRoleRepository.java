package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    // 根据用户ID查找用户角色关联
    List<UserRole> findByUserId(Integer userId);

    // 根据角色ID查找用户角色关联
    List<UserRole> findByRoleId(Integer roleId);

    // 删除用户与角色的关联
    void deleteByUserIdAndRoleId(Integer userId, Integer roleId);
}
