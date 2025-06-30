package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base {
    private String username;
    private String password;
    private String email;
    private String phone;
    private Long deptId;
}