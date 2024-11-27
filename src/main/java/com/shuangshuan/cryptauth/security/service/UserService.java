package com.shuangshuan.cryptauth.security.service;

import com.shuangshuan.cryptauth.security.entity.UserAccount;
import com.shuangshuan.cryptauth.security.request.AddUserRequest;
import com.shuangshuan.cryptauth.security.request.ChangePasswordRequest;

public interface UserService {

    UserAccount queryUserByUsername(String username);

    boolean changePassword(String username, ChangePasswordRequest changePasswordRequest);

    UserAccount addUser(AddUserRequest addUserRequest, String username);

    boolean existsById(Integer id);
}
