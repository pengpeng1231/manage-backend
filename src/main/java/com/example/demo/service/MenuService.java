package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.dto.MenuQuery;
import com.example.demo.entity.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.utils.TreeBuildUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public Menu getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    public List<Menu> getMenuTree(MenuQuery menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(menu.getName() != null, Menu::getName, menu.getName());
        queryWrapper.eq(menu.getParentId() != null, Menu::getParentId, menu.getParentId());
        queryWrapper.like(menu.getPath() != null, Menu::getPath, menu.getPath());
        queryWrapper.eq(menu.getStatus() != null, Menu::getStatus, menu.getStatus());
        queryWrapper.orderByDesc(Menu::getSortNum, Menu::getId);

        List<Menu> menus = menuMapper.selectList(queryWrapper);

        if (menus == null || menus.isEmpty()) {
            return List.of();
        }

        return buildMenuTree(getMenusWithAllParents(menus));
    }

    public Long createMenu(Menu menu) {
        menuMapper.insert(menu);
        return menu.getId();
    }

    public Long editMenu(Menu menu) {
        menuMapper.updateById(menu);
        return menu.getId();
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

    private List<Menu> getMenusWithAllParents(List<Menu> menus) {
        Set<Menu> result = new LinkedHashSet<>(menus);
        Set<Long> parentIds = menus.stream()
                .map(Menu::getParentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        while (!parentIds.isEmpty()) {
            List<Menu> parents = menuMapper.selectList(
                    new LambdaQueryWrapper<Menu>().in(Menu::getId, parentIds));

            result.addAll(parents);

            parentIds = parents.stream()
                    .map(Menu::getParentId)
                    .filter(Objects::nonNull)
                    .filter(id -> result.stream().noneMatch(m -> m.getId().equals(id)))
                    .collect(Collectors.toSet());
        }

        return new ArrayList<>(result);
    }

}
