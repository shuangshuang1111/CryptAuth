package com.shuangshuan.cryptauth.security.userdetail;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserAccount, Long> {

}
