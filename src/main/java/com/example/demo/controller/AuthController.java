package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.service.AuthService;
import com.example.demo.service.MenuService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.Result;
import com.example.demo.vo.MenuVO;
import com.example.demo.vo.UserVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/register")
    public Result<LoginDTO> register(@RequestBody LoginDTO request) {
        authService.register(request);
        return Result.ok(null, "注册成功");
    }

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody LoginDTO request, HttpServletResponse response) {
        UserVO user = authService.login(request);

        setCookie(response, user.getUsername());

        return Result.ok(user, "登录成功");
    }

    @PostMapping("/loginOrRegister")
    public Result<UserVO> loginOrRegister(@RequestBody LoginDTO request, HttpServletResponse response) {
        boolean isRegister = authService.hasUser(request);

        if (!isRegister) {
            authService.register(request);
        }

        UserVO user = authService.login(request);

        setCookie(response, user.getUsername());

        return Result.ok(user, "登录成功");
    }

    @GetMapping("/checkAuth")
    public Result<UserVO> checkAuth(@CookieValue(name = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return Result.error(1, "请登录!");
        }

        UserVO user = userService.authCheck(token);

        if (user == null) {
            return Result.error(2, "用户不存在，请注册!");
        }

        return Result.ok(user);
    }

    @GetMapping("/getUserMenuList")
    public Result<List<MenuVO>> getUserMenuList(@CookieValue(name = "token", required = false) String token) {
        UserVO user = userService.authCheck(token);
        return Result.ok(menuService.getUserMenus(user));
    }

    private void setCookie(HttpServletResponse response, String username) {
        String token = jwtUtils.generateToken(username);

        Cookie cookie = new Cookie("token", token); // 键值对
        cookie.setPath("/");                      // 对所有路径生效
        cookie.setHttpOnly(true);                 // 防止XSS攻击
//        cookie.setSecure(true);                   // 仅HTTPS传输（生产环境启用）
        cookie.setMaxAge((int) (jwtUtils.expirationMs / 1000));       // 过期时间（秒），这里是7天
        response.addCookie(cookie);               // 添加到响应
    }
}