package com.shuangshuan.cryptauth.security.auth;


import com.shuangshuan.cryptauth.security.userdetail.User;
import com.shuangshuan.cryptauth.security.userdetail.UserRepository;
import com.shuangshuan.cryptauth.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 密码加密
        userRepository.save(user);
    }

    public String authenticate(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return jwtUtil.generateToken(existingUser.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}