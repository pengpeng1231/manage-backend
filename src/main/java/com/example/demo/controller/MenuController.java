package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.IdsDTO;
import com.example.demo.dto.MenuQuery;
import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;
import com.example.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/{id}")
    public Result<Menu> getMenu(@PathVariable("id") Long id) {
        return Result.ok(menuService.getMenu(id));
    }

    @PostMapping()
    public Result<Long> createMenu(@RequestBody Menu menu) {
        return Result.ok(menuService.createMenu(menu));
    }

    @PutMapping()
    public Result<Long> editMenu(@RequestBody Menu menu) {
        return Result.ok(menuService.editMenu(menu));
    }

    @DeleteMapping()
    public Result<Integer> delMenu(@RequestBody IdsDTO idsDTO) {
        return Result.ok(menuService.delMenu(idsDTO.getIds()));
    }

    @PostMapping("/list")
    public Result<IPage<Menu>> getMenus(
            @RequestBody MenuQuery menu
    ) {
        Page<Menu> page = new Page<>(menu.getCurrent(), menu.getPageSize());

        page.setRecords(menuService.getMenuTree(menu));
        page.setTotal(menuService.getMenuTree(menu).size());

        return Result.ok(page);
    }
}
