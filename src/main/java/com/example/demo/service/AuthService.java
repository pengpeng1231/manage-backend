package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.PasswordEncoder;
import com.example.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    public void register(LoginDTO request) {
        if (userMapper.selectByUsername(request.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.insert(user); // MyBatis-Plus 的插入方法
    }

    public UserVO login(LoginDTO request) {
        User user = userMapper.selectByUsername(request.getUsername());

        if (user == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        UserVO userVO = new UserVO();
        userVO.setUsername(user.getUsername());
        userVO.setId(user.getId());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());

        return userVO;
    }

    public boolean hasUser(LoginDTO request) {
        return userMapper.selectByUsername(request.getUsername()) != null;
    }
}
