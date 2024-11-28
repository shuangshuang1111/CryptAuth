package com.shuangshuan.cryptauth.authority.repository;

import com.shuangshuan.cryptauth.authority.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    void deleteByUserId(Integer userId);

    // 使用原生 SQL 查询 roleId，根据 userId 查询
    List<UserRole> findRoleIdByUserId(Integer userId);


}
