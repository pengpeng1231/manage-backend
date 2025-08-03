package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.JwtUtils;
import com.example.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MenuService menuService;

    public UserVO getUserById(Long id) {
        User user = userMapper.selectById(id);

        if (user == null) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId(id);
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());

        return userVO;
    }

    @Transactional
    public int createUser(UserDTO user) {
        return userMapper.insert(user);
    }

    @Transactional
    public int editUser(UserDTO user) {
        return userMapper.updateById(user);
    }

    @Transactional
    public int deleteUser(Long id) {
        return userMapper.deleteById(id);
    }

    public UserVO getUser(String username) {
        User user = userMapper.selectByUsername(username);

        if (user == null) return null;

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());

        return userVO;
    }

    public UserVO authCheck(String token) {
        boolean isAuth = jwtUtils.validateToken(token);

        if (isAuth) {
            String username = jwtUtils.getUsernameFromToken(token);
            return this.getUser(username);
        }

        return null;
    }
}