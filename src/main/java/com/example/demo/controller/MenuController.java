package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.IdsDTO;
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

    @PostMapping()
    public Result<Integer> createMenu(@RequestBody Menu menu) {
        return Result.ok(menuService.createMenu(menu));
    }

    @PutMapping()
    public Result<Integer> editMenu(@RequestBody Menu menu) {
        return Result.ok(menuService.editMenu(menu));
    }

    @DeleteMapping()
    public Result<Integer> delMenu(@RequestBody IdsDTO idsDTO) {
        return Result.ok(menuService.delMenu(idsDTO.getIds()));
    }

    @GetMapping("/list")
    public Result<IPage<Menu>> getMenus(@RequestParam int current, @RequestParam int pageSize) {
        Page<Menu> page = new Page<>(current, pageSize);

        page.setTotal(1);
        page.setRecords(menuService.getMenuTree());

        return Result.ok(page);
    }
}
