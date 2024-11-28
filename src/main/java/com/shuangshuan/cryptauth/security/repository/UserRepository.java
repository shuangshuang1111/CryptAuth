package com.shuangshuan.cryptauth.security.repository;

import com.shuangshuan.cryptauth.security.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserAccount, Integer> {

    // 根据 username 查找用户
    Optional<UserAccount> findByUsername(String username);

    // 根据用户名查找用户
    boolean existsByUsername(String username);

    // 判断角色是否存在
    boolean existsById(Integer id);

}
