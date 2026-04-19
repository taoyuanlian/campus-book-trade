package com.whxy.campusbooktrade2.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.entity.User;
import com.whxy.campusbooktrade2.service.UserService;
import com.whxy.campusbooktrade2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录接口（补充role到Token）
     */
    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody User user) {
        // 1. 校验用户名密码（你的原有逻辑）
        User loginUser = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (loginUser == null || !loginUser.getPassword().equals(user.getPassword())) {
            return R.fail("用户名或密码错误");
        }

        // 2. 生成含userId和role的Token
        String token = jwtUtil.generateToken(loginUser.getId(), loginUser.getRole());

        // 3. 返回Token+用户信息
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", loginUser.getId());
        result.put("username", loginUser.getUsername());
        result.put("role", loginUser.getRole()); // 返回角色给前端
        return R.ok(result);
    }
}