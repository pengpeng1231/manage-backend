package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.PasswordEncoder;
import com.example.demo.vo.LoginVO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public void register(LoginDTO request) {
        if (userMapper.selectByUsername(request.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.insert(user); // MyBatis-Plus 的插入方法
    }

    public LoginVO login(LoginDTO request) {
        User user = userMapper.selectByUsername(request.getUsername());
        if (user == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        String token = jwtUtils.generateToken(user.getUsername());

        LoginVO loginVO = new LoginVO();

        loginVO.setUsername(user.getUsername());
        loginVO.setToken(token);

        return loginVO;
    }
}
