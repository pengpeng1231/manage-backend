package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.service.AuthService;
import com.example.demo.utils.Result;
import com.example.demo.vo.LoginVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Result<LoginDTO> register(@RequestBody LoginDTO request) {
        authService.register(request);
        return Result.ok(null, "注册成功");
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO request) {
        return Result.ok(authService.login(request), "登录成功");
    }

    @PostMapping("/loginOrRegister")
    public Result<LoginVO> loginOrRegister(@RequestBody LoginDTO request) {
        boolean isRegister = authService.hasUser(request);

        if (!isRegister) {
            authService.register(request);
        }

        return Result.ok(authService.login(request), "登录成功");
    }
}