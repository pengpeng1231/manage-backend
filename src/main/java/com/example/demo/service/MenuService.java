package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.utils.TreeBuildUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> getMenuTree() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, 1);

        List<Menu> menus = menuMapper.selectList(queryWrapper);

        if (menus == null || menus.isEmpty()) {
            return List.of();
        }

        return buildMenuTree(menus);
    }

    public int createMenu(Menu menu) {
        return menuMapper.insert(menu);
    }

    public int editMenu(Menu menu) {
        return menuMapper.updateById(menu);
    }

    public int delMenu(List<Long> ids) {
        return menuMapper.deleteByIds(ids);
    }

    private List<Menu> buildMenuTree(List<Menu> menus) {
        return TreeBuildUtils.build(menus,
                Menu::getId,
                Menu::getParentId,
                Menu::getChildren,
                Menu::setChildren
        );
    }
}
