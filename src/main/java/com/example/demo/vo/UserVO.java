package com.example.demo.vo;

import com.example.demo.entity.Department;
import lombok.Data;

@Data
public class UserVO {
    Long id;
    String username;
    String email;
    String phone;
    Department department;
}
