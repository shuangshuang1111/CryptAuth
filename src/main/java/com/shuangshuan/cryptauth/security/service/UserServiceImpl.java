package com.shuangshuan.cryptauth.security.service;

import com.shuangshuan.cryptauth.common.BusinessResponseCode;
import com.shuangshuan.cryptauth.security.entity.UserAccount;
import com.shuangshuan.cryptauth.security.repository.UserRepository;
import com.shuangshuan.cryptauth.security.request.AddUserRequest;
import com.shuangshuan.cryptauth.security.request.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 密码加密

    @Override
    public UserAccount queryUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        // 查找用户
        UserAccount user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(BusinessResponseCode.USER_NOT_FOUND.getMessage()));

        // 验证旧密码是否正确
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException(BusinessResponseCode.OLD_PASSWORD_INCORRECT.getMessage());
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setUpdatedBy(username);
        userRepository.save(user);

        return true;
    }


    /**
     * 添加新用户
     *
     * @param addUserRequest 包含新用户的信息
     * @return 创建成功的用户
     */
    public UserAccount addUser(AddUserRequest addUserRequest, String username) {
        // 检查用户是否已经存在（基于用户名或手机号等字段）
        if (userRepository.existsByUsername(addUserRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 创建新的 UserAccount 实体
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(addUserRequest.getUsername());
        userAccount.setPassword(passwordEncoder.encode(addUserRequest.getPassword())); // 加密密码
        userAccount.setMobile(addUserRequest.getMobile());
        userAccount.setCity(addUserRequest.getCity());
        userAccount.setCompany(addUserRequest.getCompany());
        userAccount.setCompanyId(addUserRequest.getCompanyId());
        userAccount.setStaffPhoto(addUserRequest.getStaffPhoto());
        userAccount.setProjectName(addUserRequest.getProjectName());
        userAccount.setCreatedBy(username);
        userAccount.setUpdatedBy(username);

        // 保存用户到数据库
        return userRepository.save(userAccount);
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }


}
