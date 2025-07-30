package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "menus")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends Base {
    private String name;
    private String path;
    private String icon;
    private int status;
    private Long parentId;
    private int sortNum;
    @TableField(exist = false)
    private List<Menu> children;
}
