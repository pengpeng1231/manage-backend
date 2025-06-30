package com.example.demo;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class TestUser {
    @Autowired
    private UserService userService;

    void select() {
        UserVO user = userService.getUserById(1L);

        if (user != null) {
            System.out.println(user.getUsername());
        } else {
            System.out.println("未查询到user");
        }
    }

    void insert() {
        UserDTO user = new UserDTO();

        user.setUsername("admin2");
        user.setPassword("admin");
        user.setEmail("sadsa@qq.com");
        user.setPhone("139312321");

        int id = userService.createUser(user);

        System.out.println(id);
    }

    void delete() {
        int id = userService.deleteUser(1L);

        System.out.println(id);
    }
}
