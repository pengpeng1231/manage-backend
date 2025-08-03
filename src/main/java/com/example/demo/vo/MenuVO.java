package com.example.demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVO {
    private Long id;
    private String name;
    private String path;
    private String icon;
    private Long parentId;
    private List<MenuVO> children;
}
